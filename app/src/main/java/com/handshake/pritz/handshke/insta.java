package com.handshake.pritz.handshke;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.handshake.pritz.handshke.R.id.postimage;


/**
 * A simple {@link Fragment} subclass.
 */
public class insta extends Fragment {

    private RecyclerView postinsta;
    private DatabaseReference mdatabase;
    private ProgressDialog progressDialog;
    public insta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insta, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("lost");
        postinsta=(RecyclerView)getActivity().findViewById(R.id.postinsta);
        postinsta.setHasFixedSize(true);
        mdatabase.keepSynced(true);
        postinsta.setLayoutManager(new LinearLayoutManager(getActivity()));


    }
    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<post,BlogViewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<post, BlogViewholder>
                (post.class,R.layout.postinsta,BlogViewholder.class,mdatabase) {
            @Override
            protected void populateViewHolder(BlogViewholder viewHolder, post model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDetail(model.getDetail());
                viewHolder.setImage(getActivity().getApplicationContext(),model.getImage());



            }
        };
        postinsta.setAdapter(firebaseRecyclerAdapter);
        mdatabase.keepSynced(true);


    }

    public static class BlogViewholder extends RecyclerView.ViewHolder{

        View view;
        public BlogViewholder(View itemView) {
            super(itemView);
            view=itemView;

        }
        public void setTitle(String title)
        {
            TextView ptitle=(TextView)view.findViewById(R.id.posttitle);
            ptitle.setText(title);
        }
        public void setDetail(String detail)
        {
            TextView pdetail=(TextView)view.findViewById(R.id.postdetail);
            pdetail.setText(detail);
        }

        public void setImage(Context ctx, String image)
        {
            ImageView post=(ImageView)view.findViewById(R.id.postimage);
            Picasso.with(ctx).load(image).into(post);

        }



    }


}
