package edu.cmu.tartan.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;


public class MessageQueue implements IQueueHandler{

	protected static final Logger gameLogger = Logger.getGlobal();

	private static final int QUEUE_SIZE = 100;

	private BlockingQueue<SocketMessage> queue;

	/**
	 *
	 */
	public MessageQueue() {
		queue = new LinkedBlockingQueue<>(QUEUE_SIZE);
	}

	/**
	 * @return
	 */
	public BlockingQueue<SocketMessage> getQueue() {
		return queue;
	}

	@Override
	public void produce (SocketMessage message) {
		try {
			queue.put(message);
		} catch (InterruptedException e) {
			gameLogger.severe("InterruptException : " + e.getMessage());
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public SocketMessage consume() {
		SocketMessage message = null;

		try{
			message = queue.take();
			if (message != null) return message;

        }catch(InterruptedException e) {
        	gameLogger.severe("InterruptException : " + e.getMessage());
        	Thread.currentThread().interrupt();
        }

		return null;
	}

	@Override
	public int clearQueue() {
		queue.clear();

		return queue.size();
	}

}
