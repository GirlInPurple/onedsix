import tkinter as tk
from tkinter import ttk
import webbrowser
import os

class Play_Client(tk.Frame):
    def __init__(self, parent, controller):
        tk.Frame.__init__(self, parent)
        frame = ttk.Frame(self, width=500, height=500)
        frame.grid()
        
        
        ClientLabel = ttk.Label(frame, text="N_ANGLE Client", font=("Arial",25))
        ClientLabel.grid(column=2, row=1)
        
        
        JVMlabel = ttk.Label(frame, text="JVM Args")
        JVMlabel.grid(column=1, row=2)

        self.JVMArgs = tk.StringVar(value="")
        JVMEntry = ttk.Entry(frame, textvariable=self.JVMArgs)
        JVMEntry.grid(column=2, row=2)
        
        JVMlink = ttk.Label(frame, text="(Click here for info)", cursor="hand2", foreground="blue", font=("Arial",10,"underline"))
        JVMlink.grid(column=3, row=2)
        JVMlink.bind(lambda: webbrowser.open(url=""))
        
        
        StartButton = ttk.Button(frame, text="Start Client", command=self.StartClient(self.JVMArgs))
        StartButton.grid(column=2, row=3)
        
    def StartClient(self, JVM):
        print(JVM)
        os.system("./launch.bat")
        pass

class Play_Server(tk.Frame):
    def __init__(self, parent, controller):
        tk.Frame.__init__(self, parent)
        frame = ttk.Frame(self, width=500, height=500)
        frame.grid()
        
        
        ClientLabel = ttk.Label(frame, text="N_ANGLE Server", font=("Arial",25))
        ClientLabel.grid(column=2, row=1)
        
        
        JVMlabel = ttk.Label(frame, text="JVM Args")
        JVMlabel.grid(column=1, row=2)

        self.JVMArgs = tk.StringVar(value="")
        JVMEntry = ttk.Entry(frame, textvariable=self.JVMArgs)
        JVMEntry.grid(column=2, row=2)
        
        JVMlink = ttk.Label(frame, text="(Click here for info)", cursor="hand2", foreground="blue", font=("Arial",10,"underline"))
        JVMlink.grid(column=3, row=2)
        JVMlink.bind(lambda: webbrowser.open(url=""))
        
        
        PFlabel = ttk.Label(frame, text="Port")
        PFlabel.grid(column=1, row=3)

        self.PFArgs = tk.IntVar(value="3600")
        PFEntry = ttk.Entry(frame, textvariable=self.PFArgs)
        PFEntry.grid(column=2, row=3)
        
        PFlink = ttk.Label(frame, text="(Click here for info)", cursor="hand2", foreground="blue", font=("Arial",10,"underline"))
        PFlink.grid(column=3, row=3)
        PFlink.bind(lambda: webbrowser.open(url="https://portforward.com/", new=1))
        
        
        StartButton = ttk.Button(frame, text="Start Server", command=self.StartServer(self.JVMArgs))
        StartButton.grid(column=2, row=4)
        
    def StartServer(self, JVM):
        print(JVM)
        pass

class Settings_Account(tk.Frame):
    def __init__(self, parent, controller):
        tk.Frame.__init__(self, parent)
        frame = ttk.Frame(self, width=500, height=500)
        frame.grid()
        
        
        Userlink = ttk.Label(frame, text="Create an account here", cursor="hand2", foreground="blue", font=("Arial",10,"underline"))
        Userlink.grid(column=2, row=1)
        Userlink.bind(lambda: webbrowser.open(url=""))
        
        
        Userlabel = ttk.Label(frame, text="Username")
        Userlabel.grid(column=1, row=2)

        self.UserArgs = tk.StringVar(value="John Doe")
        UserEntry = ttk.Entry(frame, textvariable=self.UserArgs)
        UserEntry.grid(column=2, row=2)
        
        
        Passlabel = ttk.Label(frame, text="Password")
        Passlabel.grid(column=1, row=3)

        self.PassArgs = tk.StringVar(value="Passy Mc Passface")
        PassEntry = ttk.Entry(frame, textvariable=self.PassArgs)
        PassEntry.grid(column=2, row=3)
        
        
        StartButton = ttk.Button(frame, text="Set Login Details", command=self.AttemptLogin(self.UserArgs, self.PassArgs))
        StartButton.grid(column=2, row=4)
        
    def AttemptLogin(self, User, Pass):
        print(User, Pass)
        pass

class Settings_General(tk.Frame):
    def __init__(self, parent, controller):
        tk.Frame.__init__(self, parent)
        frame = ttk.Frame(self)
        frame.grid()
        
        Settingslink = ttk.Label(frame, text="Check the wiki for an explanation on the settings", cursor="hand2", foreground="blue", font=("Arial",10,"underline"))
        Settingslink.grid(column=2, row=1)
        Settingslink.bind(lambda: webbrowser.open(url=""))
        
        
        debugVar = tk.IntVar()
        debug = ttk.Checkbutton(frame, text="Debug Mode", variable=debugVar)
        debug.grid(column=1, row=2)
        
class Install_Install(tk.Frame):
    def __init__(self, parent, controller):
        tk.Frame.__init__(self, parent)
        frame = ttk.Frame(self)
        frame.grid()
        
        PythonVer = os.system("python --version")
        JavaVer = os.system("java --version")
        
        
        
        JavaLabel = ttk.Label(frame, text=f"Your Java Version: {JavaVer}")
        JavaLabel.grid(column=1, row=1)
        
        
        PythonLabel = ttk.Label(frame, text=f"Your Python Version: {PythonVer}")
        PythonLabel.grid(column=1, row=2)
        
        
        UninstallLabel = ttk.Label(frame, text="Type your username then click \"Done\" to uninstall")
        UninstallLabel.grid(column=1, row=3)

        UninstallArgs = tk.StringVar(value=1)
        UninstallInput = ttk.Entry(frame, textvariable=UninstallArgs)
        UninstallInput.grid(column=1, row=4)
        
        UninstallButton = ttk.Button(frame,text="Done", command=self.uninstall)
        UninstallButton.grid(column=1, row=5)
        
    def uninstall():
        pass

class Main(tk.Tk):
    def __init__(self, *args, **kwargs):
        tk.Tk.__init__(self, *args, **kwargs)
        self.title("N_ANGLE_L")

        # Create the menu bar
        self.menubar = tk.Menu(self)
        self.config(menu=self.menubar)
        
        # Create the menus
        self.play_menu = tk.Menu(self.menubar, tearoff=0)
        self.menubar.add_cascade(label="Play", menu=self.play_menu)
        self.play_menu.add_command(label="Client Launcher", command=lambda: self.showFrame(Play_Client))
        self.play_menu.add_command(label="Server Launcher", command=lambda: self.showFrame(Play_Server))
        
        self.settings_menu = tk.Menu(self.menubar, tearoff=0)
        self.menubar.add_cascade(label="Settings", menu=self.settings_menu)
        self.settings_menu.add_command(label="Account", command=lambda: self.showFrame(Settings_Account))
        self.settings_menu.add_command(label="Settings", command=lambda: self.showFrame(Settings_General))
        
        self.install_menu = tk.Menu(self.menubar, tearoff=0)
        self.menubar.add_cascade(label="Install", menu=self.install_menu)
        self.install_menu.add_command(label="Install", command=lambda: self.showFrame(Install_Install))

        # Create the frames
        self.frames = {}
        for F in (Play_Client, Play_Server, Settings_Account, Settings_General, Install_Install):
            frame = F(self, self)
            self.frames[F] = frame
            frame.grid(row=0, column=0, sticky="nsew")

        # Show the first frame
        self.showFrame(Play_Client)
        
    def showFrame(self, cont):
        frame = self.frames[cont]
        frame.tkraise()
        #self.title(f"N_ANGLE_L | {cont}")

if __name__ == "__main__":
    launcher = Main()
    launcher.mainloop()