package com.longer.library.listener;

import com.longer.library.error.GlideException;

public interface RequestListener {

    void OnResourceRead();

    void OnLoadFailed(GlideException e);

}
