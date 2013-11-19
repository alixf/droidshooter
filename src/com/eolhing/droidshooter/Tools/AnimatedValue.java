package com.eolhing.droidshooter.Tools;

import java.util.Random;

public class AnimatedValue
{
	// Standard functions
	public static final int CONSTANT = 0;
	public static final int LINEAR = 1;
	public static final int EXPONENTIAL = 2;
	// Periodic functions
	public static final int SINUSOIDAL = 3;
	public static final int TRIANGULAR = 4;
	public static final int RECTANGULAR = 5;
	// Random functions
	public static final int RANDOM = 6;
	// Interpolation functions
	public static final int INTERPOLATION = 7;

	private static final Random random = new Random();

	int mType;
	float mStart;
	float mEnd;
	float mParameter;
	long mDuration;
	Clock mClock;

	// Non-animated value constructor
	public AnimatedValue(float value)
	{
		mType = CONSTANT;
		mStart = value;
		mClock = new Clock();
	}

	// Animation Constructor for constant, linear, exponential and random
	// functions
	public AnimatedValue(int type, float start, float parameter)
	{
		if (type == CONSTANT || type == LINEAR || type == EXPONENTIAL || type == RANDOM)
		{
			mType = type;
			mStart = start;
			mParameter = parameter;
			mClock = new Clock();
		}
	}

	// Animation Constructor for interpolation, sinusoidal, triangular and
	// rectangular functions
	public AnimatedValue(int type, float startValue, float endValue, long duration)
	{
		if (type == INTERPOLATION || type == SINUSOIDAL || type == TRIANGULAR || type == RECTANGULAR)
		{
			mType = type;
			mStart = startValue;
			mEnd = endValue;
			mDuration = duration;
			mClock = new Clock();
		}
	}

	public float getValue()
	{
		float time = mClock.getTime() / 1000.f;
		float value = 0.f;

		switch (mType)
		{
		case CONSTANT:
			value = mStart;
			break;
		case INTERPOLATION:
			value = time > mDuration / 1000.f ? mEnd : (time / (mDuration / 1000.f)) * (mEnd - mStart) + mStart;
			break;
		case LINEAR:
			value = mStart + time * mParameter;
			break;
		case EXPONENTIAL:
			value = mStart + (float) Math.pow(time, mParameter);
			break;
		case SINUSOIDAL:
			value = (float) Math.sin(time * (1 / (mDuration / 1000.f) * Math.PI * 2)) * (mEnd - mStart) / 2 + (mEnd - mStart) / 2 + mStart;
			break;
		case TRIANGULAR:
			// value =;
			break;
		case RECTANGULAR:
			// value =;
			break;
		case RANDOM:
			value = random.nextFloat() * (mParameter - mStart) + mStart;
			break;
		default:
			value = mStart;
		}
		return value;
	}

	public void setTimeFactor(float factor)
	{
		mClock.setFactor(factor);
	}

	public void reverse(boolean resetClock)
	{
		float tmp = mStart;
		mStart = mEnd;
		mEnd = tmp;
		if (resetClock)
			reset();
	}

	public void reset()
	{
		mClock.reset();
	}
}
