package com.pku.testcases;

import com.pku.base.TestBase;
import com.pku.page.MessageBuilder;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class MessageBuilderTests  extends TestBase {


    @Test
    public void testNameMessage() {
        String name = "Jake";
        MessageBuilder messageBuilder = new MessageBuilder();
        assertEquals(MessageBuilder.HELLO + name, messageBuilder.getMessage(name));
    }

    @Test
    public void testEmptyMessage() {
        MessageBuilder messageBuilder = new MessageBuilder();
        assertEquals(MessageBuilder.PROVIDED, messageBuilder.getMessage(" "));
    }

    @Test
    public void testNullMessage() {
        MessageBuilder messageBuilder = new MessageBuilder();
        assertEquals(MessageBuilder.PROVIDED, messageBuilder.getMessage(null));
    }

}
