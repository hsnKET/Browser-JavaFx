package callbacks;

import matier.Favorite;

public interface SideBarListener {
    void onItemClicked(Favorite item);
    void onFavoriteItemRemoved(Favorite favorite);
}
