package com.eolhing.droidshooter.Tools;

public class Clock
{
	private long mTimeBuffer;
	private long mLastTime;
	private float mFactor;

	public Clock()
	{
		mTimeBuffer = 0;
		mLastTime = System.currentTimeMillis();
		mFactor = 1.f;
	}

	public Clock(float factor)
	{
		mTimeBuffer = 0;
		mLastTime = System.currentTimeMillis();
		mFactor = factor;
	}

	public void reset()
	{
		reset(mFactor);
	}

	public void reset(float factor)
	{
		mTimeBuffer = 0;
		mLastTime = System.currentTimeMillis();
		mFactor = factor;
	}

	public void setFactor(float factor)
	{
		computeTimeBuffer();
		this.mFactor = factor;
	}

	public float getFactor()
	{
		return mFactor;
	}

	public long getTime()
	{
		return computeTimeBuffer();
	}

	private long computeTimeBuffer()
	{
		long currentTime = System.currentTimeMillis();
		mTimeBuffer += mFactor * (currentTime - mLastTime);
		mLastTime = currentTime;
		return mTimeBuffer;
	}
}