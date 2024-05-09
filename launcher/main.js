const { app, BrowserWindow, ipcMain, nativeTheme } = require('electron/main')
const { downloadFile } = require('./network');
const { spawn } = require('child_process');
const path = require('node:path');
const fs = require('fs-extra');

let mainWindow;

function createEnviron() {
  fs.ensureDir("./instances/")
  fs.ensureDir("./cache/")
  fs.ensureFile("./settings.json")
  downloadFile(new URL("https://raw.githubusercontent.com/GirlInPurple/onedsix/master/assets/icon.png"), "./cache/icon.png")
}

function createWindow() {
  
  mainWindow = new BrowserWindow({
    width: 1600,
    height: 1200,
    webPreferences: {
      preload: path.join(__dirname, 'preload.js')
    }
  })
  mainWindow.setMenu(null);
  mainWindow.loadFile('index.html')
  mainWindow.setIcon("./cache/icon.png")

  // Opens the DevTools.
  mainWindow.webContents.openDevTools()
}

ipcMain.handle('dark-mode:toggle', () => {
  if (nativeTheme.shouldUseDarkColors) {
    nativeTheme.themeSource = 'light';
  } else {
    nativeTheme.themeSource = 'dark';
  }
  return nativeTheme.shouldUseDarkColors;
})

ipcMain.handle('java:start', (event, app) => {

  console.log(app)
  const javaProcess = spawn('java', ['-jar', app]);

  javaProcess.stdout.on('data', (data) => {
    mainWindow.webContents.send('javaLogger', data.toString());
    console.error(data.toString())
  });

  javaProcess.stderr.on('data', (data) => {
    mainWindow.webContents.send('javaLogger', data.toString());
    console.error(data.toString())
  });

  javaProcess.on('close', (code) => {
    mainWindow.webContents.send('javaLogger', `Exit Code: ${code.toString()}`);
    console.error(`Exit Code: ${code.toString()}`)
  });
})

app.whenReady().then(() => {
  createEnviron();
  createWindow();

  app.on('activate', function () {
    if (BrowserWindow.getAllWindows().length === 0) createWindow();
  })
})

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') app.quit();
})
