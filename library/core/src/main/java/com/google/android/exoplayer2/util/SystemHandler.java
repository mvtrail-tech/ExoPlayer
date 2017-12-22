/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.exoplayer2.util;

import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/** The standard implementation of {@link HandlerWrapper}. */
/* package */ final class SystemHandler implements HandlerWrapper {

  /* package */ static final class Factory implements HandlerWrapper.Factory {

    @Override
    public HandlerWrapper createHandler(Looper looper, Callback callback) {
      return new SystemHandler(new android.os.Handler(looper, callback));
    }
  }

  private final android.os.Handler handler;

  private SystemHandler(android.os.Handler handler) {
    this.handler = handler;
  }

  @Override
  public Looper getLooper() {
    return handler.getLooper();
  }

  @Override
  public Message obtainMessage(int what) {
    return handler.obtainMessage(what);
  }

  @Override
  public Message obtainMessage(int what, Object obj) {
    return handler.obtainMessage(what, obj);
  }

  @Override
  public Message obtainMessage(int what, int arg1, int arg2) {
    return handler.obtainMessage(what, arg1, arg2);
  }

  @Override
  public Message obtainMessage(int what, int arg1, int arg2, Object obj) {
    return handler.obtainMessage(what, arg1, arg2, obj);
  }

  @Override
  public boolean sendEmptyMessage(int what) {
    return handler.sendEmptyMessage(what);
  }

  @Override
  public boolean sendEmptyMessageDelayed(int what, long delayMs, long referenceTimeMs) {
    long targetMessageTime = referenceTimeMs + delayMs;
    long remainingDelayMs = targetMessageTime - SystemClock.elapsedRealtime();
    if (remainingDelayMs <= 0) {
      return handler.sendEmptyMessage(what);
    } else {
      return handler.sendEmptyMessageDelayed(what, remainingDelayMs);
    }
  }

  @Override
  public void removeMessages(int what) {
    handler.removeMessages(what);
  }

  @Override
  public void removeCallbacksAndMessages(Object token) {
    handler.removeCallbacksAndMessages(token);
  }

  @Override
  public boolean post(Runnable runnable) {
    return handler.post(runnable);
  }

  @Override
  public boolean postDelayed(Runnable runnable, long delayMs) {
    return handler.postDelayed(runnable, delayMs);
  }
}