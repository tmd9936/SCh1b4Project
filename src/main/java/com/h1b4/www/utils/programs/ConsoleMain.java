package com.h1b4.www.utils.programs;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.h1b4.www.utils.ProgramPaths;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class ConsoleMain {
	//https://github.com/danijel3/SpeechProsody
	//@Parameter(names = "-i", description = "Input WAV file.", required = true)
	private String input_path;
	//private String input_path = "";

	//@Parameter(names = "-o", description = "Output JSON file.", required = true)
	private String output_path = "c:/tmp/test/full.json";
	//private String output_path = "";

	//@Parameter(names = "-t", description = "Path to tmp directory.")
	private String tmp_dir_path;

	//@Parameter(names = "-m", description = "Skip the Momel step during computation.")
	private boolean skip_momel = false;

	//@Parameter(names = "-h", description = "This help.", help = true)
	private boolean help = false;

	//@Parameter(names = "-d", description = "Print debug of intermediary steps.")
	private boolean debug = false;

	final static Logger logger = LoggerFactory.getLogger(ConsoleMain.class);
	
	
	/**
	 * 
	 * @param input_path praat로 정보 얻을 파일의 경로
	 * @param tmp_dir_path tmp디렉토리 경로
	 */
	public ConsoleMain(String input_filename,String tmp_dir_path) {
		this.input_path = "c:/tmp/test/"+input_filename;
		this.tmp_dir_path = "c:/home/plugin_momel-intsint/analysis/"+tmp_dir_path;
	}
	
	
	public ConsoleMain() {
		// TODO Auto-generated constructor stub
	}
	
	public Vector<Double> getData() {
		try {
			//ConsoleMain main = new ConsoleMain();
			JCommander parse = new JCommander(this);
			if (this.help) {
				parse.usage();
				return null;
			}
			return run();
		} catch (ParameterException e) {
			System.out.println("Argument parsing error: " + e.getMessage());
			System.exit(1);
			return null;
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return null;
	}
	
	/*public static void main(String[] args) {
		try {
			ConsoleMain main = new ConsoleMain();
			JCommander parse = new JCommander(main, args);
			if (main.help) {
				parse.usage();
				return;
			}
			main.run();
		} catch (ParameterException e) {
			System.out.println("Argument parsing error: " + e.getMessage());
			System.exit(1);
			return;
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}*/

	public Vector<Double> run() {

		/*File paths_file = new File("paths.conf");
		if (paths_file.exists()) {
			try {
				ProgramPaths.loadFromFile(new File("paths.conf"));
			} catch (IOException e) {
				return null;
			}
		} else {
			try {
				ProgramPaths.saveToFile(new File("paths.conf"));
			} catch (IOException e) {
			}
		}

		if (!ProgramPaths.check()) {
			logger.error("Some programs couldn't be found! Exiting!");
			return null;
		}*/

		logger.info("Starting SpeechProsody...");
		
		//여기 오류 아마 디렉토리가 안 만들어져서 오류인듯
		
		
		
		File tmp_dir = new File(tmp_dir_path);
		if(!tmp_dir.isDirectory()) {
			tmp_dir.mkdir();
		}
		File wav_file = new File(input_path);
		
		//File out_file = new File(output_path);

		try {

			Vector<Praat.PitchMark> pitchmarks = Praat.momel_pitch(wav_file, tmp_dir);

			if (debug) {
				for (Praat.PitchMark pitchmark : pitchmarks) {
					System.out.println("PM> " + pitchmark.time + "s " + pitchmark.frequency + "Hz "
							+ pitchmark.intensity + "dB " + pitchmark.strength + "Pa");
				}
				logger.debug("확인", pitchmarks);
			}

			Vector<Momel.Point> momel_points;
			Vector<Double> pitches = null;

			if (skip_momel)
				momel_points = Momel.convert(pitchmarks, true);
			else {

				pitches = Praat.pitchmarks_to_pitchstream(pitchmarks);

				if (debug) {
					for (Double pitch : pitches) {
						System.out.print(pitch + ", ");
					}
					System.out.println();
				}
				//다시 확인
				/*logger.debug("다시 확인");
				momel_points = Momel.momel(pitches);*/
			}

			/*if (debug) {
				for (Momel.Point point : momel_points) {
					System.out.println("MP> " + point.time + "ms " + point.frequency + "Hz ");
				}
			}
*/
			//Intsint.Result result = Intsint.intsint(momel_points, tmp_dir);
			return pitches;

		} catch (IOException e) {
			logger.error("Main error.", e);
		}
		return null;
	}
}
