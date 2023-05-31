package com.kunminx.puremusic.data.bean;

/**
 * Create by KunMinX at 2022/7/15
 */
public class DownloadState {
  public final boolean isForgive;
  public final int progress;

  public DownloadState() {
    this.isForgive = false;
    this.progress = 0;
  }

  public DownloadState(boolean isForgive, int progress) {
    this.isForgive = isForgive;
    this.progress = progress;
  }
}
