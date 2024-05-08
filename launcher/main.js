const { app, BrowserWindow, ipcMain, nativeTheme  } = require('electron/main')
const path = require('node:path')
const { spawn } = require('child_process');

let mainWindow;

function createWindow () {
  
  mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      preload: path.join(__dirname, 'preload.js')
    }
  })
  mainWindow.loadFile('index.html')

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
  createWindow();

  app.on('activate', function () {
    if (BrowserWindow.getAllWindows().length === 0) createWindow();
  })
})

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') app.quit();
})
