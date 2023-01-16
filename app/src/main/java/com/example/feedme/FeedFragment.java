    package com.example.feedme;

    import android.os.Bundle;

    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.RecyclerView;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    public class FeedFragment extends Fragment {
        private RecyclerView feedRecyclerView;
        private TextView feedTitle;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            feedRecyclerView = container.findViewById(R.id.feedFragment_feeds_rv);
            feedTitle = container.findViewById(R.id.feedFragment_title_tv);

            return inflater.inflate(R.layout.fragment_feed, container, false);
        }




    }