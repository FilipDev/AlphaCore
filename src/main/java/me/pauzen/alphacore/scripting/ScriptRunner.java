/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.alphacore.scripting;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptRunner {

    private static ScriptEngineManager scriptManager = new ScriptEngineManager();
    private static ScriptEngine        javaScript    = scriptManager.getEngineByName("JavaScript");

    public ScriptRunner() {
        javaScript.put("server", Bukkit.getServer());
    }

    public void runScript(Player runner, Script script) {
        javaScript.put("me", runner);
        javaScript.put("world", runner.getWorld());
        try {
            javaScript.eval(script.getCompleteScript());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public static double evaluate(String expression, double... varValues) throws ScriptException {
        return Double.valueOf(javaScript.eval(String.format(expression, varValues)).toString());
    }
}
