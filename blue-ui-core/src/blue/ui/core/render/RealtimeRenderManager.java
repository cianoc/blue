/*
 * blue - object composition environment for csound
 * Copyright (C) 2013
 * Steven Yi <stevenyi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package blue.ui.core.render;

import blue.BlueData;
import blue.BlueSystem;
import blue.SoundLayer;
import blue.event.PlayModeListener;
import blue.gui.ExceptionDialog;
import blue.mixer.Mixer;
import blue.score.layers.LayerGroup;
import blue.services.render.CsoundBinding;
import blue.services.render.RealtimeRenderService;
import blue.services.render.RealtimeRenderServiceFactory;
import blue.settings.RealtimeRenderSettings;
import blue.soundObject.PolyObject;
import blue.soundObject.SoundObject;
import blue.soundObject.SoundObjectException;
import blue.utility.ObjectUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.WindowManager;

/**
 *
 * @author stevenyi
 */
public final class RealtimeRenderManager {

    private boolean looping = false;
    private static RealtimeRenderManager instance = null;
    private ArrayList<PlayModeListener> listeners = new ArrayList<>();
    private ArrayList<PlayModeListener> blueLiveListeners = new ArrayList<>();
    private RealtimeRenderServiceFactory currentRenderServiceFactory = null;
    private RealtimeRenderService currentRenderService = null;
    private RealtimeRenderService currentBlueLiveRenderService = null;
    private PlayModeListener realtimeListener;
    private PlayModeListener blueLiveListener;
    private boolean auditioning = false;
    private boolean shuttingDown = false;

    private RealtimeRenderManager() {
        realtimeListener = (int playMode) -> {
            if (shuttingDown) {
                return;
            }
            if (playMode == PlayModeListener.PLAY_MODE_STOP) {
                auditioning = false;
            }
            for (PlayModeListener listener : listeners) {
                listener.playModeChanged(playMode);
            }
        };
        blueLiveListener = (int playMode) -> {
            if (shuttingDown) {
                return;
            }
            for (PlayModeListener listener : blueLiveListeners) {
                listener.playModeChanged(playMode);
            }
        };
    }

    public static RealtimeRenderManager getInstance() {
        if (instance == null) {
            instance = new RealtimeRenderManager();
        }
        return instance;
    }

    public void addPlayModeListener(PlayModeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removePlayModeListener(PlayModeListener listener) {
        listeners.remove(listener);
    }

    public void addBlueLivePlayModeListener(PlayModeListener listener) {
        if (!blueLiveListeners.contains(listener)) {
            blueLiveListeners.add(listener);
        }
    }

    public void removeBlueLivePlayModeListener(PlayModeListener listener) {
        blueLiveListeners.remove(listener);
    }

    public void renderProject(BlueData data) {
        this.renderProject(data, false);
    }

    protected void renderProject(BlueData data, boolean auditioning) {

        if (isRendering()) {
            stopRendering();
        }

        if (data == null) {
            this.auditioning = false;
            return;
        }

        this.auditioning = auditioning;

        StatusDisplayer.getDefault().setStatusText(BlueSystem.getString(
                "message.generatingCSD"));

        RealtimeRenderServiceFactory factory = RealtimeRenderSettings.getInstance().renderServiceFactory;

        if (currentRenderServiceFactory != factory
                || currentRenderService == null
                || currentRenderService.getClass() != factory.getRenderServiceClass()) {
            if (currentRenderService != null) {
                currentRenderService.removePlayModeListener(realtimeListener);
            }
            currentRenderServiceFactory = factory;
            currentRenderService = factory.createInstance();
            currentRenderService.addPlayModeListener(realtimeListener);
        }

        currentRenderService.setData(data);
        try {
            currentRenderService.render();
        } catch (SoundObjectException soe) {
            ExceptionDialog.showExceptionDialog(
                    WindowManager.getDefault().getMainWindow(),
                    soe);
        }
    }

    public void renderForBlueLive(BlueData data) {

        if (currentBlueLiveRenderService != null && currentBlueLiveRenderService.isRunning()) {
            currentBlueLiveRenderService.stop();
        }

        if (data == null) {
            return;
        }

        StatusDisplayer.getDefault().setStatusText(BlueSystem.getString(
                "message.generatingCSD"));

        RealtimeRenderServiceFactory factory = RealtimeRenderSettings.getInstance().renderServiceFactory;

        if (currentRenderServiceFactory != factory
                || currentBlueLiveRenderService == null
                || currentBlueLiveRenderService.getClass() != factory.getRenderServiceClass()) {
            if (currentBlueLiveRenderService != null) {
                currentBlueLiveRenderService.removePlayModeListener(blueLiveListener);
            }
            currentRenderServiceFactory = factory;
            currentBlueLiveRenderService = factory.createInstance();
            currentBlueLiveRenderService.addPlayModeListener(blueLiveListener);
        }

        currentBlueLiveRenderService.addBinding(new BlueLiveBinding(data));
        currentBlueLiveRenderService.setData(data);
        try {
            currentBlueLiveRenderService.renderForBlueLive();
        } catch (SoundObjectException soe) {
            ExceptionDialog.showExceptionDialog(
                    WindowManager.getDefault().getMainWindow(),
                    soe);
        }

    }

    public void addBlueLiveBinding(CsoundBinding binding) {
        if (currentBlueLiveRenderService != null) {
            currentBlueLiveRenderService.addBinding(binding);
        }
    }

    public void auditionSoundObjects(BlueData data, SoundObject[] soundObjects) {

        if (soundObjects == null || soundObjects.length == 0) {
            return;
        }

        if (isRendering()) {
            stopRendering();
        }

        BlueData tempData = new BlueData(data);

        List<PolyObject> path = null;
        for(LayerGroup slg : data.getScore()) {
            if(slg instanceof PolyObject) {
                path = getPolyObjectPath((PolyObject)slg, soundObjects[0]);
                if(path != null) {
                    break;
                }
            }
        }

        if(path == null) {
            throw new RuntimeException("Error: unable to find root PolyObject...");
        } 

        PolyObject tempPObj = new PolyObject(true);
        tempData.getScore().getAllLayers().stream().forEach(e -> e.clearScoreObjects());
        
        PolyObject base = tempPObj;
        SoundLayer sLayer = tempPObj.newLayerAt(-1);

//        if(path.size() > 1) {
//            for(PolyObject pObj : path) {
//                PolyObject tpo = new PolyObject(true);
//                SoundLayer tsl = tpo.newLayerAt(-1);
//                tpo.setTimeState(pObj.getTimeState().clone());
//                tpo.setTimeBehavior(pObj.getTimeBehavior());
//                tpo.setStartTime(pObj.getStartTime());
//                tpo.setSubjectiveDuration(pObj.getSubjectiveDuration());
//                base.get(0).add(tpo);
//                base = tpo;
//            }
//
//            if(base.getTimeBehavior() == SoundObject.TIME_BEHAVIOR_SCALE ||
//                    base.getTimeBehavior() == SoundObject.TIME_BEHAVIOR_REPEAT) {
//                List<SoundObject> objs = base.getSoundObjects(false);
//                SoundObject last = null;
//                for(SoundObject temp : objs) {
//                    if(last == null) {
//                        last = temp;
//                    } else {
//                        double end = last.getStartTime() + last.getSubjectiveDuration(); 
//                        double end1 = temp.getStartTime() + temp.getSubjectiveDuration();
//                        if(end1 > end) {
//                            last = temp;
//                        }
//                    }  
//                }
//                GenericScore dummy = new GenericScore();
//                dummy.setStartTime(last.getStartTime());
//                dummy.setSubjectiveDuration(last.getSubjectiveDuration());
//                dummy.setText("");
//                base.get(0).add(dummy);
//            }
//        } 


        double minTime = Double.MAX_VALUE;
        double maxTime = Double.MIN_VALUE;

        for (int i = 0; i < soundObjects.length; i++) {
            SoundObject sObj = soundObjects[i];
            double startTime = sObj.getStartTime();
            double endTime = startTime + sObj.getSubjectiveDuration();

            if (startTime < minTime) {
                minTime = startTime;
            }

            if (endTime > maxTime) {
                maxTime = endTime;
            }

            base.get(0).add(sObj.deepCopy());
        }

        tempData.getScore().add(tempPObj);

        Mixer m = tempData.getMixer();

        if (m.isEnabled()) {
            maxTime += m.getExtraRenderTime();
        }

        tempData.setRenderStartTime(minTime);
        tempData.setRenderEndTime(maxTime);

        renderProject(tempData, true);
    }

    public void stopRendering() {
        if (isRendering()) {
            currentRenderService.stop();
            realtimeListener.playModeChanged(PlayModeListener.PLAY_MODE_STOP);
        }
    }

    public void stopBlueLiveRendering() {
        if (isBlueLiveRendering()) {
            currentBlueLiveRenderService.stop();
            blueLiveListener.playModeChanged(PlayModeListener.PLAY_MODE_STOP);
        }
    }

    public void stopAuditioning() {
        if (isAuditioning()) {
            stopRendering();
        }
    }

    public boolean isAuditioning() {
        return (isRendering() && auditioning);
    }

    public boolean isRendering() {
        return (currentRenderService != null && currentRenderService.isRunning());
    }

    public boolean isBlueLiveRendering() {
        return (currentBlueLiveRenderService != null && currentBlueLiveRenderService.isRunning());
    }

    public void passToStdin(String score) {
        if (isBlueLiveRendering()) {
            currentBlueLiveRenderService.passToStdin(score);
        }
    }

    public void shutdown() {
        shuttingDown = true;
        stopRendering();
        stopBlueLiveRendering();
    }

    private List<PolyObject> getPolyObjectPath(PolyObject pObj, SoundObject soundObject) {
        List<SoundObject> allSObj = pObj.getSoundObjects(true);
        List<PolyObject> retVal = null;
        if (allSObj.contains(soundObject)) {
            retVal = new ArrayList<>();
            retVal.add(pObj);
        } else {
            List<SoundObject> pObjs = allSObj.stream().
                    filter(a -> a instanceof PolyObject).
                    collect(Collectors.toList());
            for(SoundObject obj : pObjs) {
                PolyObject tempPObj = (PolyObject)obj;
                retVal = getPolyObjectPath(tempPObj, soundObject);
                if(retVal != null) {
                    retVal.add(0, pObj);
                    break;
                }
            }
        }

        return retVal;
    }
}
