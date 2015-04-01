/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore.utils.commonnms;

import me.pauzen.alphacore.utils.UnsafeBukkitClasses;
import me.pauzen.alphacore.utils.reflection.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Packet {

    private Object     packet;
    private Class      packetClass;
    private Reflection reflection;
    private Class[]  params;
    private Object[] values;

    public Packet(String packet) throws IllegalAccessException, InstantiationException {
        this.packetClass = UnsafeBukkitClasses.getNMSClass(packet);
        this.reflection = new Reflection(this);
    }

    public Packet(Object object) {
        this.packet = object;
        this.packetClass = object.getClass();
        this.reflection = new Reflection(object);
    }

    public Object getPacket() {
        return this.packet;
    }

    public Packet initPacket(Class[] params, Object[] values) {
        try {
            Constructor desiredConstructor = packetClass.getConstructor(params);
            this.packet = desiredConstructor.newInstance(values);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Packet init() {
        this.initPacket(params, values);
        params = null;
        values = null;
        return this;
    }

    public Packet initParams(Class... params) {
        this.params = params;
        return this;
    }

    public Packet initValues(Object... values) {
        this.values = values;
        return this;
    }

    public Packet set(String field, Object value) {
        reflection.setValue(field, value);
        return this;
    }

    public Object getField(String field) {
        return reflection.getValue(field);
    }

    public Reflection getReflection() {
        return this.reflection;
    }

    public Object callMethod(String method, Object... values) {
        return this.reflection.callMethod(method, values);
    }

}
