@LimanProcessor(processorClassName = "MyClass", messageMaxLevel = MessageLevel.WARNING)
@LimanMessageLevel(MessageLevel.ERROR)
package org.liman.test.processor;

import org.liman.MessageLevel;
import org.liman.annotation.LimanMessageLevel;
import org.liman.annotation.LimanProcessor;