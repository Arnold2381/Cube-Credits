package com.example.cubestop;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link History#newInstance} factory method to
 * create an instance of this fragment.
 */
public class History extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView Outer_hist;
    Map<String,List <Months>> map =  new HashMap<String,List <Months>>();
    List<List <Inner_hist_item> > ChildItemList
            = new ArrayList<>();
    List<String> month_name=new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public History() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment History.
     */
    // TODO: Rename and change types and number of parameters
    public static History newInstance(String param1, String param2) {
        History fragment = new History();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dataManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_history,container,false);

        Outer_hist= view.findViewById(R.id.parentRecycler);

        Outer_hist.setLayoutManager(new LinearLayoutManager(view.getContext()));



    return view;
    }
    private List<Outer_hist_item> ParentItemList() {

        int ii=0;
        List<Outer_hist_item> itemList
                = new ArrayList<>();
        for (List<Inner_hist_item> i:
             ChildItemList) {
            itemList.add(new Outer_hist_item(month_name.get(ii++),i));


        }
        return itemList;
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
//    private List<Inner_hist_item> ChildItemList()
//    {
//
//        List<Inner_hist_item> ChildItemList
//                = new ArrayList<>();
//
//        ChildItemList.add(new Inner_hist_item(R.drawable.ic_grocery,"fff","fjnfjnf","fifnfk"));
//        ChildItemList.add(new Inner_hist_item(R.drawable.ic_grocery,"fff","fjnfjnf","fifnfk"));
//        ChildItemList.add(new Inner_hist_item(R.drawable.ic_grocery,"fff","fjnfjnf","fifnfk"));
//        ChildItemList.add(new Inner_hist_item(R.drawable.ic_grocery,"fff","fjnfjnf","fifnfk"));
//
//        return ChildItemList;
//    }
    private void ChildItemList()  {


        Iterator mapiterator=map.entrySet().iterator();
        while(mapiterator.hasNext())
        {
            Map.Entry mapElement=(Map.Entry)mapiterator.next();
            List <Months> lnew= (List<Months>) mapElement.getValue();
            List<Inner_hist_item> childnew=new ArrayList<>();
            for (Months i:lnew)
            {

                    if(i.getService().equalsIgnoreCase("Grocery"))
                    childnew.add(new Inner_hist_item(R.drawable.ic_grocery,"Bought "+i.getService()+" items more than Rs "+String.valueOf(i.getPrice()),i.getShop()+"- "+i.getTimestamp(),i.getCredits()));
                    else if(i.getService().equalsIgnoreCase("Food"))
                childnew.add(new Inner_hist_item(R.drawable.ic_food,"Dined for more than more than Rs "+String.valueOf(i.getPrice()),i.getShop()+"- "+i.getTimestamp(),i.getCredits()));
                    else if(i.getService().equalsIgnoreCase("Eco Friendly"))
                        childnew.add(new Inner_hist_item(R.drawable.ic_eco,"Eco friendly purchase",i.getShop()+"- "+i.getTimestamp(),i.getCredits()));
            }
            ChildItemList.add(childnew);
            month_name.add((String) mapElement.getKey());

        }
        Log.d("jfjbfjbf",String.valueOf(ChildItemList));
        Log.d("dkdkndkd",String.valueOf(month_name));

    }
    private void dataManager()
    {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot user = dataSnapshot.child("Users").child("Abhishek123").child("History");
                List <Months> arr = new ArrayList<Months>();
                int i=0;
                for(DataSnapshot ds: user.getChildren())
                {
                    Log.d("kkl",String.valueOf(ds.getKey()));
                    for (DataSnapshot ds1:ds.getChildren())
                    {
                        Log.d("kkl",String.valueOf(ds1));
                        arr.add(new Months(ds.getKey(),ds1.getKey(),ds1.child("Credits").getValue(String.class),ds1.child("Price").getValue(Double.class),ds1.child("Shop").getValue(String.class),ds1.child("Timestamp").getValue(String.class)));
                    }
                }
                Log.d("hubuu",String.valueOf(arr.size()));
                for(Months k:arr)
                {
                    map.put(k.getMonth(),new LinkedList<Months>());

                }
                for(Months k:arr)
                {
                    map.get(k.getMonth()).add(k);

                }

                Log.e("dknkn",String.valueOf(map));
                ChildItemList();
                Outer_hist_Adapter parentAdapter= new Outer_hist_Adapter(ParentItemList());
                Outer_hist.setAdapter(parentAdapter);


    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}