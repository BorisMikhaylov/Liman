@LimanProcessor(processorClassName = "LimanTestClass", messageMaxLevel = MessageLevel.WARNING)
@LimanMessageLevel(MessageLevel.ERROR)
package org.liman.test.annotations.processor;

import org.liman.MessageLevel;
import org.liman.annotation.LimanMessageLevel;
import org.liman.annotation.LimanProcessor;