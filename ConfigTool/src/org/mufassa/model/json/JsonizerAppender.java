package org.mufassa.model.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Log4j appender used during deserialization by the Jsonizer
 */
public class JsonizerAppender extends AppenderSkeleton {
	private final List<LoggingEvent> loggingEvents = new ArrayList<>();

	public JsonizerAppender() {
	}

	@Override
	protected void append(LoggingEvent pEvent) {
		loggingEvents.add(pEvent);
	}

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return true;
	}
	
	private boolean hasLevel(Level pLevel) {
		for (LoggingEvent event : loggingEvents) {
			if(event.getLevel().isGreaterOrEqual(pLevel)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasWarnings() {
		return hasLevel(Level.WARN);
	}
	
	public boolean hasErrors() {
		return hasLevel(Level.ERROR);
	}
	
	public List<String> getMessages() {
		List<String> list = new ArrayList<>(loggingEvents.size());
		for (LoggingEvent event : loggingEvents) {
			list.add(String.format("%s - %s", event.getLevel(), event.getRenderedMessage()));
		}
		return list;
	}
}
