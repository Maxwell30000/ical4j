/*
 * $Id$
 *
 * Created on 11/03/2007
 *
 * Copyright (c) 2007, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.fortuna.ical4j.model.property.Uid;

/**
 * Unit tests for {@link UidGenerator}.
 * @author Ben Fortuna
 *
 */
public class UidGeneratorTest extends TestCase {

    private UidGenerator generator;
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        generator = new UidGenerator("1");
    }
    
    /**
     * Test method for {@link net.fortuna.ical4j.util.UidGenerator#generateUid()}.
     */
    public void testGenerateUid() throws InterruptedException {
        final List uids = new ArrayList();
        
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        uids.add(generator.generateUid());
                    }
                }
            });
        }
        
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
        
        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }

        for (int i = 0; i < uids.size(); i++) {
            Uid uid = (Uid) uids.get(i);
            for (int j = 0; j < uids.size(); j++) {
                if (j != i) {
                    assertFalse(uid.equals(uids.get(j)));
                }
            }
        }
    }
}