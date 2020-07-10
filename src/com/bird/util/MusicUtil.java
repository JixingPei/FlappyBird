package com.bird.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

/**
 * 音乐工具类
 * 
 * @author Kingyu wav音频：JDK提供的类可直接解码 mp3音频：JDK没有提供支持，需要使用第三方的工具包
 * 
 */
public class MusicUtil {

	private static AudioClip fly;
	private static AudioClip crash;
	private static AudioClip score;

	// 装载音乐资源
	public static void load() {
		try {
			fly = Applet.newAudioClip(new File("sources/wav/fly.wav").toURL());
			crash = Applet.newAudioClip(new File("sources/wav/crash.wav").toURL());
			score = Applet.newAudioClip(new File("sources/wav/score.wav").toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	//wav播放
	public static void playFly() {
		fly.play();
	}
	
	public static void playCrash() {
		crash.play();
	}
	
	public static void playScore() {
		score.play();
	}

}

//mp3装载方法
/*
 * import javazoom.jl.decoder.JavaLayerException; import
 * javazoom.jl.player.Player
 * 
 * private static Player mp3;
 * 
 * public static void load() { 
 *     InputStream in = new FileInputStream(file);
 *     BufferedInputStream buffer = new BufferedInputStream(in) 
 *     mp3 = new Player(buffer); 
 * }
 * 
 * public static void playMp3(){
 *     mp3.play();
 *     mp3.close();
 * }
 * 
 * public static void reset(){
 *     InputStream in = new FileInputStream(file);
 *     BufferedInputStream buffer = new BufferedInputStream(in) 
 *     mp3 = new Player(buffer); 
 * }
 */