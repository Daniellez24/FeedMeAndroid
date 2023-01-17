    package com.example.feedme;

    import android.os.Bundle;

    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import com.example.feedme.adapters.FeedAdapter;
    import com.example.feedme.models.Recipe;

    import java.util.LinkedList;
    import java.util.List;


    public class FeedFragment extends Fragment {
        private RecyclerView feedRecyclerView;
        private TextView feedTitle;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_feed, container, false);

            feedRecyclerView = view.findViewById(R.id.feedFragment_feeds_rv);
            feedTitle = view.findViewById(R.id.feedFragment_title_tv);

            feedRecyclerView.hasFixedSize();
            feedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            //TODO separate this into view model

            List<Recipe> recipesList = new LinkedList<Recipe>();
            recipesList.add(new Recipe(R.drawable.feed,R.drawable.feed, "Test", "This is body \n This is body" ));
            recipesList.add(new Recipe(R.drawable.feed,R.drawable.feed, "Test1", "tesad" ));
            recipesList.add(new Recipe(R.drawable.feed,R.drawable.feed, "Test2", "tesad" ));
            recipesList.add(new Recipe(R.drawable.feed,R.drawable.feed, "Test3", "tesad" ));
            recipesList.add(new Recipe(R.drawable.feed,R.drawable.feed, "Test4", "tesad" ));
            recipesList.add(new Recipe(R.drawable.feed,R.drawable.feed, "Test5", "tesad" ));

            FeedAdapter adapter = new FeedAdapter(recipesList);
            feedRecyclerView.setAdapter(adapter);
            return view;
        }


    }