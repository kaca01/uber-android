package com.example.ubermobileapp.fragments.history;

import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.adapters.CommentAdapter;
import com.example.ubermobileapp.models.pojo.communication.Review;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends ListFragment {

    private List<Review> reviews = new ArrayList<>();


    public CommentsFragment() { }

    public CommentsFragment(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CommentAdapter commentAdapter =  new CommentAdapter(getActivity(), reviews);
        setListAdapter(commentAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_of_comments, container, false);
    }
}