package com.h1b4.www.utils.programs;



import java.io.ByteArrayOutputStream;

import com.h1b4.www.utils.ProgramLauncher;

public class Which {

    public static String which(String program) {

        String[] cmd = new String[]{"which",program};

        ProgramLauncher launcher = new ProgramLauncher(cmd);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        launcher.setStdoutStream(bos);

        launcher.run();

        if(launcher.getReturnValue()!=0)
            return null;

        return bos.toString();
    }
}

