/**
 * 
 */
package com.oxxo.adx.appinstall.service;

import java.io.IOException;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;

/**
 * @author Administrator
 *
 */
public class AppInstallServiceImpl implements AppInstallService {

	private static final Logger LOG = LoggerFactory.getLogger(AppInstallServiceImpl.class);

	private BinaryDecoder binaryDecoder;

	@Override
	public <T> T deserializeMsg(String msg, Class<T> cls) {
		try {
			DatumReader<T> userDatumReader = new SpecificDatumReader<T>(cls);
			if (binaryDecoder == null) {
				binaryDecoder = DecoderFactory.get().binaryDecoder(msg.getBytes(Charsets.UTF_8), binaryDecoder);
			}
			return userDatumReader.read(null, binaryDecoder);
		} catch (IOException e) {
			LOG.error("read data from msg got error.");
			return null;
		}

	}

}
