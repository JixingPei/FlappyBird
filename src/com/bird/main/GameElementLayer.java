package com.bird.main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

/**
 * 游戏中各种元素层的类
 * 
 * @author Kingyu
 *
 */

public class GameElementLayer {
	private List<Pipe> pipes;

	public GameElementLayer() {
		pipes = new ArrayList<>();

	}

	/**
	 * 初始化元素层
	 */
//	private void initElements() {
//	}

	public void draw(Graphics g, Bird bird) {
		for (int i = 0; i < pipes.size(); i++) {
			// 如果可见则绘制，不可见则归还
			Pipe pipe = pipes.get(i);
			if (pipe.isVisible()) {
				pipe.draw(g, bird);
			} else {
				Pipe remove = pipes.remove(i);
				PipePool.giveBack(remove);
				i--;
			}
		}

		// 碰撞检测
		isCollideBird(bird);
		pipeBornLogic(bird);
	}

	/**
	 * 添加元素的逻辑 当容器中添加的最后一个元素完全显示到屏幕后，添加下一对 1:成对地相对地出现，空隙高度为窗口高度的1/6
	 * 2:每对的间隔距离为屏幕高度的1/4 3:每一个障碍物的高度的取值范围[1/8~5/8]
	 */
	public static final int VERTICAL_INTERVAL = Constant.FRAME_HEIGHT / 5;
	public static final int HORIZONTAL_INTERVAL = Constant.FRAME_HEIGHT >> 2;
	public static final int MIN_HEIGHT = Constant.FRAME_HEIGHT >> 3;
	public static final int MAX_HEIGHT = (Constant.FRAME_HEIGHT >> 3) * 5;

	private void pipeBornLogic(Bird bird) {
		if (bird.isDead()) {
			// 鸟死后不再添加管道
			return;
		}
		if (pipes.size() == 0) {
			// 容器为空，则添加一对管道
			int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // 随机生成管道高度

			Pipe top = PipePool.get();
			top.setAttribute(Constant.FRAME_WIDTH, 0, topHeight, Pipe.TYPE_TOP_NORMAL, true);

			Pipe bottom = PipePool.get();
			bottom.setAttribute(Constant.FRAME_WIDTH, topHeight + VERTICAL_INTERVAL,
					Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL, Pipe.TYPE_BOTTOM_NORMAL, true);

			pipes.add(top);
			pipes.add(bottom);
		} else {
			// 判断最后一对管道是否完全进入游戏窗口
			Pipe lastPipe = pipes.get(pipes.size() - 1); // 获得容器中最后一个管道
			if (lastPipe.isInFrame()) {
				int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // 随机生成管道高度
				int x = lastPipe.getX() + HORIZONTAL_INTERVAL;

				Pipe top = PipePool.get();
				top.setAttribute(x, 0, topHeight, Pipe.TYPE_TOP_NORMAL, true);

				Pipe bottom = PipePool.get();
				bottom.setAttribute(x, topHeight + VERTICAL_INTERVAL,
						Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL, Pipe.TYPE_BOTTOM_NORMAL, true);

				pipes.add(top);
				pipes.add(bottom);
			}
		}
	}

	/**
	 * 判断元素和小鸟是否发生碰撞
	 * 
	 * @param bird
	 * @return
	 */
	public boolean isCollideBird(Bird bird) {
		if (bird.isDead()) {
			// 鸟死后不再判断
			return false;
		}
		for (int i = 0; i < pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			// 判断两个矩形是否有交集
			if (pipe.getPipeRect().intersects(bird.getBirdRect())) {
				bird.BirdFall();
				return true;
			}
		}
		return false;
	}
	
	//重置元素层
	public void reset() {
		for(int i=0; i<pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			PipePool.giveBack(pipe);
		}
		pipes.clear();
	}
}