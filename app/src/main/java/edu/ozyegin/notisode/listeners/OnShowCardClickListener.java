package edu.ozyegin.notisode.listeners;

import android.view.View;

import edu.ozyegin.notisode.fragments.ShowFragment;
import edu.ozyegin.notisode.objects.Show;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Batuhan on 11.5.2015.
 */
public class OnShowCardClickListener implements Card.OnCardClickListener {
    private Show show;

    public OnShowCardClickListener(Show show) {
        this.show = show;
    }

    @Override
    public void onClick(Card card, View view) {
        ShowFragment showFragment = new ShowFragment();

    }
}
