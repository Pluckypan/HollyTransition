package engineer.echo.transition.fragment;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import engineer.echo.transition.App;
import engineer.echo.transition.R;

/**
 * GalleryAdapter.java
 * Info: 相机
 * Created by Plucky(plucky@echo.engineer) on 2019/6/13 - 10:15 AM
 * more about me: http://www.1991th.com
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ItemHolder> {

    private GalleryListener mListener;
    private int size = (App.sScreenSize.x - App.dpToPx(80)) / 3;
    private int margin = App.dpToPx(10);

    public GalleryAdapter(GalleryListener listener) {
        this.mListener = listener;
    }

    private ItemHolder createHolder(ViewGroup parent, int viewType) {
        ImageView itemView = new ImageView(parent.getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
        layoutParams.setMargins(margin, margin, margin, margin);
        itemView.setLayoutParams(layoutParams);
        itemView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        itemView.setImageResource(R.drawable.girl_0);
        return new ItemHolder(itemView);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        String shareName = "image-" + position;
        ViewCompat.setTransitionName(holder.itemView, shareName);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(final ImageView itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(itemView, getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface GalleryListener {
        void onItemClick(ImageView imageView, int position);
    }
}
