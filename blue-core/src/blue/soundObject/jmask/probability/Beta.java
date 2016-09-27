/*
 * blue - object composition environment for csound
 * Copyright (c) 2000-2007 Steven Yi (stevenyi@gmail.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by  the Free Software Foundation; either version 2 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING.LIB.  If not, write to
 * the Free Software Foundation Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307 USA
 */
package blue.soundObject.jmask.probability;

import blue.soundObject.jmask.Table;
import blue.utility.XMLUtilities;
import electric.xml.Element;
import electric.xml.Elements;

public class Beta implements ProbabilityGenerator {

    private double a = 0.1;// a -> Breite bei Null

    private double b = 0.1; // b -> Breite bei Eins

    private Table aTable;

    private Table bTable;

    private boolean aTableEnabled = false;

    private boolean bTableEnabled = false;

    public Beta() {
        aTable = new Table();
        bTable = new Table();
        aTable.getPoint(0).setValue(0.1);
        aTable.getPoint(1).setValue(0.1);
        bTable.getPoint(0).setValue(0.1);
        bTable.getPoint(1).setValue(0.1);
    }

    public Beta(Beta beta) {
        a = beta.a;
        b = beta.b;
        aTable = new Table(beta.aTable);
        aTable = new Table(beta.bTable);
        aTableEnabled = beta.aTableEnabled;
        bTableEnabled = beta.bTableEnabled;
    }

    public static ProbabilityGenerator loadFromXML(Element data) {
        Beta retVal = new Beta();

        Elements nodes = data.getElements();

        while (nodes.hasMoreElements()) {
            Element node = nodes.next();
            String nodeName = node.getName();
            switch (nodeName) {
                case "a":
                    retVal.a = XMLUtilities.readDouble(node);
                    break;
                case "b":
                    retVal.b = XMLUtilities.readDouble(node);
                    break;
                case "aTableEnabled":
                    retVal.aTableEnabled = XMLUtilities.readBoolean(node);
                    break;
                case "bTableEnabled":
                    retVal.bTableEnabled = XMLUtilities.readBoolean(node);
                    break;
                case "table":
                    String tableId = node.getAttributeValue("tableId");
                    switch (tableId) {
                        case "aTable":
                            retVal.aTable = Table.loadFromXML(node);
                            break;
                        case "bTable":
                            retVal.bTable = Table.loadFromXML(node);
                            break;
                    }
                    break;
            }
        }

        return retVal;
    }

    @Override
    public Element saveAsXML() {
        Element retVal = new Element("probabilityGenerator");
        retVal.setAttribute("type", getClass().getName());

        retVal.addElement(XMLUtilities.writeDouble("a", a));
        retVal.addElement(XMLUtilities.writeDouble("b", b));
        retVal.addElement(XMLUtilities.writeBoolean("aTableEnabled", aTableEnabled));
        retVal.addElement(XMLUtilities.writeBoolean("bTableEnabled", bTableEnabled));

        Element aTableNode = aTable.saveAsXML();
        aTableNode.setAttribute("tableId", "aTable");

        Element bTableNode = bTable.saveAsXML();
        bTableNode.setAttribute("tableId", "bTable");

        retVal.addElement(aTableNode);
        retVal.addElement(bTableNode);

        return retVal;
    }

    @Override
    public String getName() {
        return "Beta";
    }

    @Override
    public double getValue(double time, java.util.Random rnd) {
        double x1, x2, yps1, yps2, sum;

        double localA, localB;

        if (aTableEnabled) {
            localA = aTable.getValue(time);
        } else {
            localA = a;
        }

        if (bTableEnabled) {
            localB = bTable.getValue(time);
        } else {
            localB = b;
        }

        do {
            x1 = rnd.nextDouble();
            x2 = rnd.nextDouble();
            yps1 = Math.pow(x1, (1.0 / localA));
            yps2 = Math.pow(x2, (1.0 / localB));
            sum = yps1 + yps2;
        } while (sum > 1.0);

        return yps1 / sum;

    }

    // a -> Breite bei Null
    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    // b -> Breite bei Eins
    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public Table getATable() {
        return aTable;
    }

    public void setATable(Table aTable) {
        this.aTable = aTable;
    }

    public Table getBTable() {
        return bTable;
    }

    public void setBTable(Table bTable) {
        this.bTable = bTable;
    }

    public boolean isATableEnabled() {
        return aTableEnabled;
    }

    public void setATableEnabled(boolean aTableEnabled) {
        this.aTableEnabled = aTableEnabled;
    }

    public boolean isBTableEnabled() {
        return bTableEnabled;
    }

    public void setBTableEnabled(boolean bTableEnabled) {
        this.bTableEnabled = bTableEnabled;
    }

    @Override
    public Beta deepCopy() {
        return new Beta(this);
    }
}
