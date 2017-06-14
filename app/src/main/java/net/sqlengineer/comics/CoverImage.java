package net.sqlengineer.comics;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import net.sqlengineer.comics.data.Image;
import net.sqlengineer.comics.data.Result;

import java.util.List;

/**
 * Created by druckebusch on 6/7/17.
 */

public class CoverImage extends AppCompatImageView {

    Result mResult = null;


    public CoverImage(Context context) {
        super(context);
    }

    public CoverImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoverImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setResult(Result result) {
        mResult = result;
        if (result.getCoverBitmap() != null) {
            this.setImageBitmap(result.getCoverBitmap());
            return;
        }

        if (result != null && result.getImages() != null) {
            List<Image> images = result.getImages();
            if (images != null && images.size() > 0) {
                Image cover = images.get(0);
                String url = ImageUtils.getUrl(cover.getPath(), cover.getExtension(), ImageUtils.Sizes.XLARGE, ImageUtils.AspectRatios.PORTRAIT);
                ImageUtils.setBitmapAsync(result, url);
            }
        }
    }

    public void saveCoverBitmapInResult(Bitmap bitmap) {
        if (mResult != null) {
            mResult.setCoverBitmap(bitmap);
        }
    }
}
