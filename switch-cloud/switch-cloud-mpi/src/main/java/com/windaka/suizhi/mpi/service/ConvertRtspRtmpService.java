package com.windaka.suizhi.mpi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

public interface ConvertRtspRtmpService {
    String queryRtmpVideoUrl(String tokenId,String xqCode,String capDevCode,String capDevChannel) throws OssRenderException;
    boolean closeRtmpVideoStream(String tokenId,String xqCode,String capDevCode,String capDevChannel) throws OssRenderException;
}
