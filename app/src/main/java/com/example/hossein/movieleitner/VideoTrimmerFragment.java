package com.example.hossein.movieleitner;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import adapter.VocabularyAdapter;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;
import model.VocabularyModel;
import utils.Const;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoTrimmerFragment extends Fragment implements OnTrimVideoListener, VocabularyAdapter.OnClickListener {


    public static final String DATA_LIST = "list";
    private RecyclerView recyclerView;
    private K4LVideoTrimmer videoTrimmer;
    private ArrayList<VocabularyModel> vocabList;
    private FrameLayout videoTrimmer_container_layout;
    private FrameLayout recyclerView_container_layout;

    public VideoTrimmerFragment() {
        // Required empty public constructor
    }

    public static VideoTrimmerFragment getInstance(ArrayList<VocabularyModel> vocabList) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DATA_LIST, vocabList);

        VideoTrimmerFragment fragment = new VideoTrimmerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_trimmer, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        videoTrimmer = ((K4LVideoTrimmer) view.findViewById(R.id.timeLine));
        recyclerView_container_layout = (FrameLayout) view.findViewById(R.id.recycler_view_wrapper);
        videoTrimmer_container_layout = (FrameLayout) view.findViewById(R.id.videoTrimmerWrapper);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        final FragmentManager fm = getActivity().getSupportFragmentManager();
        final int backStackCount = fm.getBackStackEntryCount() + 1;

        final FragmentTransactions fragmentTransactions = ((FragmentTransactions) getActivity());

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int count = fm.getBackStackEntryCount();
                if (count < backStackCount) {
                    fragmentTransactions.transaction("fragmentFinished");
                }
            }
        });

        vocabList = getArguments().getParcelableArrayList(DATA_LIST);

        if (vocabList == null) {
            Toast.makeText(getContext(), "خطا: لیست کلمات null میباشد.", Toast.LENGTH_SHORT).show();
            return;
        }

        VocabularyAdapter adapter = new VocabularyAdapter(getContext(), vocabList);
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setKeepScreenOn(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());





        if (videoTrimmer != null && VocabularyModel.getMoviePath() != null) {
            videoTrimmer.setVideoURI(Uri.parse(VocabularyModel.getMoviePath()));
//            videoTrimmer.setMaxDuration(1);
//            videoTrimmer.setMaxDuration(1000);
            videoTrimmer.setOnTrimVideoListener(this);
            videoTrimmer.setKeepScreenOn(true);
            videoTrimmer.setStartPosition((int) VocabularyModel.getStartSubtitlePosition() -5000);
            videoTrimmer.setEndPosition((int) VocabularyModel.getEndSubtitlePosition() + 5000);
            File destination = new File(Const.MOVIES_DIRECTORY);
            destination.mkdirs();
            videoTrimmer.setDestinationPath(destination.getPath());


        }


    }

    @Override
    public void onTrimStarted() {
        Toast.makeText(getContext(), "Trim start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResult(Uri uri) {
        Toast.makeText(getContext(), "Trim finish", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void cancelAction() {
        Toast.makeText(getContext(), "Trim cancel", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), "Trim error", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(int id) {
        switch (id) {
            case R.id.add_movie_part_to_leitner:
                videoTrimmer_container_layout.setVisibility(View.VISIBLE);
                recyclerView_container_layout.setVisibility(View.GONE);
                break;

            case R.id.continue_movie:
                getFragmentManager().popBackStack();
                break;
        }
    }

    public interface FragmentTransactions {
        void transaction(String transactionCode);
    }


}
