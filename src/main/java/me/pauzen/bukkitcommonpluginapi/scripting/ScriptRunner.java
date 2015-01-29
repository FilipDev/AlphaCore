package me.pauzen.bukkitcommonpluginapi.scripting;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptRunner {

    private ScriptEngineManager scriptManager = new ScriptEngineManager();
    private ScriptEngine javaScript = scriptManager.getEngineByName("JavaScript");

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
}
