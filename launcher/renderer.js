/**
 * This file is loaded via the <script> tag in the index.html file and will
 * be executed in the renderer process for that window. No Node.js APIs are
 * available in this process because `nodeIntegration` is turned off and
 * `contextIsolation` is turned on. Use the contextBridge API in `preload.js`
 * to expose Node.js functionality from the main process.
 */
import appendLog from './index.html'

console.log($(document).children)

document.getElementById('toggle-dark-mode').addEventListener('click', async () => {
    const isDarkMode = await window.darkMode.toggle();
    document.getElementById('theme-source').innerHTML = isDarkMode;
})

document.getElementById('desktop').addEventListener('click', () => {
    window.java.start("desktop.jar")
})

document.getElementById('server').addEventListener('click', () => {
    window.java.start("server.jar")
})

window.java.onLog(loggerData => {
    console.log(loggerData);
    appendLog("color=\"red\"", loggerData)
});