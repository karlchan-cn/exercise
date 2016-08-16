/**
 * 
 */
package cn.com.karl.apache.poi;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <p>Project:			<p>
 * <p>Module:			<p>
 * <p>Description:		<p>
 *
 * @author chenjinlong
 * @date 2016年8月11日 下午7:08:31
 */
public class TaskInfo {
	
	String name;

	String className;

	String function;

	String schedule;

	boolean mutilIdc;

	boolean writeMaster;

	String highRedisKey;

	String lowRedisKey;

	String switchName;

	boolean stopable;

	String logKeyword;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName( String className ) {
		this.className = className;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction( String function ) {
		this.function = function;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule( String schedule ) {
		this.schedule = schedule;
	}

	public boolean isMutilIdc() {
		return mutilIdc;
	}

	public void setMutilIdc( boolean mutilIdc ) {
		this.mutilIdc = mutilIdc;
	}

	public boolean isWriteMaster() {
		return writeMaster;
	}

	public void setWriteMaster( boolean writeMaster ) {
		this.writeMaster = writeMaster;
	}

	public String getHighRedisKey() {
		return highRedisKey;
	}

	public void setHighRedisKey( String highRedisKey ) {
		this.highRedisKey = highRedisKey;
	}

	public String getLowRedisKey() {
		return lowRedisKey;
	}

	public void setLowRedisKey( String lowRedisKey ) {
		this.lowRedisKey = lowRedisKey;
	}

	public String getSwitchName() {
		return switchName;
	}

	public void setSwitchName( String switchName ) {
		this.switchName = switchName;
	}

	public boolean isStopable() {
		return stopable;
	}

	public void setStopable( boolean stopable ) {
		this.stopable = stopable;
	}

	public String getLogKeyword() {
		return logKeyword;
	}

	public void setLogKeyword( String logKeyword ) {
		this.logKeyword = logKeyword;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString( this, ToStringStyle.MULTI_LINE_STYLE );
	}

}
