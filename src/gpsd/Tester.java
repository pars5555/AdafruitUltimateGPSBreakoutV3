/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsd;

/*
 * #%L
 * GPSd4Java
 * %%
 * Copyright (C) 2011 - 2012 Taimos GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import de.taimos.gpsd4java.api.ObjectListener;
import de.taimos.gpsd4java.backend.GPSdEndpoint;
import de.taimos.gpsd4java.backend.ResultParser;
import de.taimos.gpsd4java.types.ATTObject;
import de.taimos.gpsd4java.types.DeviceObject;
import de.taimos.gpsd4java.types.DevicesObject;
import de.taimos.gpsd4java.types.SATObject;
import de.taimos.gpsd4java.types.SKYObject;
import de.taimos.gpsd4java.types.TPVObject;
import de.taimos.gpsd4java.types.subframes.SUBFRAMEObject;

/**
 * This class provides tests during the startup phase of GPSd4Java<br>
 * It will later be replaced by JUnit Tests
 *
 * created: 17.01.2011
 *
 */
public class Tester {

    private Tester() {
    }

    public static void main(final String[] args) {
        try {
            String host = "localhost";
            int port = 2947;

            final GPSdEndpoint ep = new GPSdEndpoint(host, port, new ResultParser());

            ep.addListener(new ObjectListener() {

                @Override
                public void handleTPV(final TPVObject tpv) {
                    System.err.println(tpv);
                }

                @Override
                public void handleSKY(final SKYObject sky) {
                    //Tester.log.info("SKY: {}", sky);
                   
                    for (final SATObject sat : sky.getSatellites()) {
                        //System.err.println(sat);
                        //.log.info("  SAT: {}", sat);
                    }
                }

                @Override
                public void handleSUBFRAME(final SUBFRAMEObject subframe) {
                  
                   // System.err.println(subframe);
                    //Tester.log.info("SUBFRAME: {}", subframe);
                }

                @Override
                public void handleATT(final ATTObject att) {
                   
                   // System.err.println(att);

//Tester.log.info("ATT: {}", att);
                }

                @Override
                public void handleDevice(final DeviceObject device) {
                   
                    //System.err.println(device);
                    //Tester.log.info("Device: {}", device);
                }

                @Override
                public void handleDevices(final DevicesObject devices) {
                  
                    for (final DeviceObject d : devices.getDevices()) {
                       // System.err.println(d);
                        //Tester.log.info("Device: {}", d);
                    }
                }
            });

            ep.start();
            for (int i = 0; i < 30; i++) {
                Thread.sleep(1000);
                ep.watch(true, true);
            }

			//Tester.log.info("Version: {}", ep.version());
            //Tester.log.info("Watch: {}", ep.watch(true, true));
            //Tester.log.info("Poll: {}", ep.poll());
            Thread.sleep(1000);
        } catch (final Exception e) {
            //Tester.log.error("Problem encountered", e);
        }
    }
}
