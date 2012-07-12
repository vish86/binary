/*
 * SnapLogic - Data Integration 
 *
 * Copyright (C) 2006 - 2010, SnapLogic, Inc.  All rights reserved.
 *
 * This program is licensed under the terms of 
 * the SnapLogic Commercial Subscription agreement. 
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 *
 *
 * $Id: BinRead.java 10963 2010-03-17 19:16:47Z dmitri $
 */

package Binary3.Binary3;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.snaplogic.cc.Capabilities;
import org.snaplogic.cc.Capability;
import org.snaplogic.cc.ComponentAPI;
import org.snaplogic.cc.InputView;
import org.snaplogic.cc.OutputView;
import org.snaplogic.cc.prop.DictProp;
import org.snaplogic.cc.prop.ListProp;
import org.snaplogic.cc.prop.SimpleProp;
import org.snaplogic.cc.prop.SimpleProp.SimplePropType;
import org.snaplogic.common.Record;
import org.snaplogic.common.exceptions.SnapComponentException;
import org.snaplogic.util.FileUtils;

public class BinReadInput extends ComponentAPI {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAPIVersion() {
		return "1.0";
	}


	@Override
	public String getDescription() {
		return "Binary Read from InputViews Component";
	}

	@Override
	public String getLabel() {
		return "Binary Reader Component";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getComponentVersion() {
		return "1.0";
	}

	@Override
	public Capabilities getCapabilities() {
		return new Capabilities() {{
			put(Capability.OUTPUT_VIEW_ALLOW_BINARY, true);
		}};
	}

	@Override
	public void createResourceTemplate() {
		setPropertyDef("filename", new SimpleProp("File name", 
				SimplePropType.SnapString, "The URI of the file to be read",
				null,
				true));

		SimpleProp num = new SimpleProp("Number of times", SimplePropType.SnapNumber);
		DictProp dprop = new DictProp("Generate Data", num, 
				"Generates the data to be written out, instead of reading from a file",
				true);
		dprop.put("num", num);
		dprop.put("data", new ListProp("Data"));
		setPropertyDef("gen_data", dprop);
		setPropertyDef("use_gen", new SimpleProp("Use flag", SimplePropType.SnapBoolean));
		// By default, we use the File reading approach.
		setPropertyValue("use_gen", false);
	}



	@Override
	public void execute(Map<String, InputView> inputViews, Map<String, OutputView> outputViews) {
		//String filename       = (String) getPropertyValue("filename");
		OutputView outputView = outputViews.values().iterator().next();

		//FileInputStream fis = null;

		InputView inView = null;
		if(inputViews.size() > 0) {
			inView = inputViews.values().iterator().next();
		}

		String startdocs = "{ \"docs\": [";
		String enddocs = "{\"_id\":\"test\"} ] }"; 
		boolean flag = false;

		
		if(inView != null) {	
			//insane hack - try bulk mode
			for (String ctype: outputView.listBinaryContentTypes()) {
				outputView.writeBinary(startdocs.getBytes(), 0, startdocs.getBytes().length, ctype);
				flag = true;
			}
			
			while(true) {
				
				Record inputRec = inView.readRecord();
				
				if(inputRec == null) {
					break;
				}
				
				
				
				// Do only if tempString begins with Anonymous
				String temp_String = inputRec.getString("JSON_String").toString();
				if(temp_String == ("{Anonymous}"))
					break;
				String JSON_String = null;
				try
				{
				if(temp_String.toLowerCase().contains("anonymous"))
				{
					JSON_String = temp_String.substring(13, temp_String.length()-1);
				} else
				{
					JSON_String = temp_String;
				}
				debug("789JSON_String: " + JSON_String);	
				}
				catch(Exception f)
				{
					debug(f.getLocalizedMessage());
				}

				try {
					//fis = new FileInputStream(filename);
					//Ajay
//					byte[] readData = JSON_String.getBytes();
//					//readData = JSON_String.getBytes(); 

					byte[] readData = new byte[JSON_String.getBytes().length];
					readData = JSON_String.getBytes();
					
					
					//int sz = fis.read(readData);
					//while (sz != -1) {
					for (String ctype: outputView.listBinaryContentTypes()) {
						//Ajay
//						for(int i=0; i < readData.length; i+=500)
//						{
//							int end = 499;
//							if(i+500 > readData.length)
//							{
//								end = readData.length - i -1;
//							}
//							outputView.writeBinary(readData, i, end, ctype);
//						}
							
						 
							outputView.writeBinary(readData, 0, readData.length, ctype);

						outputView.writeBinary(",".getBytes(), 0, ",".getBytes().length, ctype);
					}
					//sz = fis.read(readData);
				}

				catch (Exception e) {
					throw new SnapComponentException(e.getLocalizedMessage());
				} finally {
					//FileUtils.closeQuietly(fis);
				}
				
			}
			//insane hack - try bulk mode
			for (String ctype: outputView.listBinaryContentTypes()) {
				outputView.writeBinary(enddocs.getBytes(), 0, enddocs.getBytes().length, ctype);
				//flag = true;
			}

		}
		outputView.completed();
	}
}
