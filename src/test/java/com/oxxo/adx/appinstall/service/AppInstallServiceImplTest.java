/**
 * 
 */
package com.oxxo.adx.appinstall.service;

import java.io.ByteArrayOutputStream;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Assert;
import org.junit.Test;

import com.oppo.dc.data.avro.generated.AppInstallEvent;

/**
 * @author Administrator
 *
 */
public class AppInstallServiceImplTest {
	AppInstallServiceImpl svc = new AppInstallServiceImpl();

	@Test
	public void testSvc() throws Exception {
		AppInstallEvent event = AppInstallEvent.newBuilder().setAction(1).setAppId("").setImei("").build();
		DatumWriter<AppInstallEvent> writer = new SpecificDatumWriter<AppInstallEvent>(AppInstallEvent.class);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		writer.write(event, encoder);
		encoder.flush();
		out.close();
		byte[] serializedBytes = out.toByteArray();
		AppInstallEvent outEvent = svc.deserializeMsg(new String(serializedBytes), AppInstallEvent.class);
		Assert.assertTrue(event.equals(outEvent));
	}
}
