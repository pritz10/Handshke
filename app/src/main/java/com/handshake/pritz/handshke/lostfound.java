package com.handshake.pritz.handshke;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class lostfound extends Fragment {

    ImageButton lost;
    EditText ltitle ;
    EditText ldetail ;
    Button button;
    private  Uri imageurl=null;
    private static final int GALLERY_REQUEST=1;
    private StorageReference mStorageRef;

    private ProgressDialog progressDialog;

    public lostfound() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_lostfound, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lost = (ImageButton)getActivity().findViewById(R.id.lost);
        ltitle = (EditText) getActivity().findViewById(R.id.ltitle);
        ldetail = (EditText) getActivity().findViewById(R.id.ldetail);
        button=(Button) getActivity().findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startposting();
            }
        });
        progressDialog = new ProgressDialog(getActivity());
        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, GALLERY_REQUEST);
            }
        });
    }
        private void startposting(){
            final DatabaseReference mref=FirebaseDatabase.getInstance().getReference().child("lost");
            mStorageRef=FirebaseStorage.getInstance().getReference();
            progressDialog.setMessage("Just Wait.....");
            final String title = ltitle.getText().toString().trim();
            final String detail = ldetail.getText().toString().trim();
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(detail) && imageurl != null)
            {
                progressDialog.show();
                StorageReference reference=mStorageRef.child("Blog_images").child(imageurl.getLastPathSegment());
                reference.putFile(imageurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                        Uri downloaduri=taskSnapshot.getDownloadUrl();

                        DatabaseReference databaseReference=mref.push();
                        databaseReference.child("title").setValue(title);
                        databaseReference.child("detail").setValue(detail);
                        databaseReference.child("image").setValue(downloaduri.toString());
                        progressDialog.dismiss();


                        insta c=new insta();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.content,c).commit();

                        // Intent i=new Intent(Lost_Found.this,LostFound.class);
                        //startActivity(i);



                    }
                });

            }
        }
        @Override
        public  void onActivityResult(int requestCode,int resultCode,Intent data)
        {
            super.onActivityResult(requestCode,resultCode,data);
            if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK)
            {
                imageurl=data.getData();
                lost.setImageURI(imageurl);
            }
        }

    }


