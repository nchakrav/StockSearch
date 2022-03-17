package com.nikhilchakravartula.stocksearch.swipe;

import android.content.Context;
import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.models.FavoriteStockModel;
import com.nikhilchakravartula.stocksearch.models.PortfolioStockModel;
import com.nikhilchakravartula.stocksearch.sections.favorite.FavoriteSection;
import com.nikhilchakravartula.stocksearch.sections.portfolio.PortfolioSection;

import java.util.Collections;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class SwipeDragCallbackHelper extends ItemTouchHelper.Callback {

    Context context;
    FavoriteSection favoriteSection;
    PortfolioSection portfolioSection;
    SectionedRecyclerViewAdapter sectionAdapter;
    int dragDirs;
    int swipeDirs;
    public SwipeDragCallbackHelper(Context context,
                             PortfolioSection portfolioSection,
                             FavoriteSection favoriteSection,
                             SectionedRecyclerViewAdapter sectionAdapter,
                             int dragDirs,
                             int swipeDirs) {

        this.context = context;
        this.favoriteSection = favoriteSection;
        this.portfolioSection = portfolioSection;
        this.sectionAdapter = sectionAdapter;
        this.dragDirs = dragDirs;
        this.swipeDirs = swipeDirs;

    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int pos = viewHolder.getBindingAdapterPosition();
        int portfolioStart = 0;
        int portFolioEnd = portfolioSection.getContentItemsTotal();
        int favStart = portFolioEnd+1;
        int favEnd = favStart+favoriteSection.getContentItemsTotal();

        if(pos == portfolioStart || pos == favStart)
        {
            return makeMovementFlags(0,0);
        }
        else if(pos <= portFolioEnd)
        {
            return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN,0);
        }
        else
        {
            return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);

        }

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        int portfolioStart = 0;
        int portFolioEnd = portfolioSection.getContentItemsTotal();
        int favStart = portFolioEnd+1;
        int favEnd = favStart+favoriteSection.getContentItemsTotal();

        int from = viewHolder.getBindingAdapterPosition();
        int to = target.getBindingAdapterPosition();
        Log.d("from to",""+from+","+to);
        Log.d("fav se, port se",""+favStart+","+favEnd+": "+portfolioStart+","+portFolioEnd);
        if(from> portfolioStart && from<=portFolioEnd && to>portfolioStart && to<=portFolioEnd)
        {
            Collections.swap(portfolioSection.getItemList(), from-1,to-1);
            sectionAdapter.notifyItemMoved(from,to);
            return true;

        }
        else if(from>favStart && from<=favEnd && to>favStart && to<=favEnd)
        {

            Collections.swap(favoriteSection.getItemList(), from-favStart-1,to-favStart-1);
            sectionAdapter.notifyItemMoved(from,to);
            return true;
        }
        return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        new RecyclerViewSwipeDecorator
                .Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(context,R.color.red))
                .addSwipeLeftActionIcon(R.drawable.ic_delete)
                .create().decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        int position = viewHolder.getBindingAdapterPosition();
        List<FavoriteStockModel> favoriteSectionItemList = favoriteSection.getItemList();
        List<PortfolioStockModel> portfolioSectionItemList = portfolioSection.getItemList();
        Log.d("SWIPE","On swipe called: "+portfolioSectionItemList.size()+","
                +favoriteSectionItemList.size()+" ,"+position);
        if(position<=portfolioSectionItemList.size())
        {
            return;
        }
        else
        {
            int offset = position-portfolioSectionItemList.size()-2;
            Log.d("Offset",""+offset);
            favoriteSectionItemList.remove(offset);
            sectionAdapter.notifyItemRemoved(position);
        }
    }
}