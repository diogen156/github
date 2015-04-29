package com.example.marko.zagreen;


import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Pieced together from:
 * Android samples: com.example.android.apis.view.ExpandableList1
 * http://androidword.blogspot.com/2012/01/how-to-use-expandablelistview.html
 * http://stackoverflow.com/questions/6938560/android-fragments-setcontentview-alternative
 * http://stackoverflow.com/questions/6495898/findviewbyid-in-fragment-android
 */
public class FragmentAdvices extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_advice, null);
        ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.list);
        elv.setAdapter(new SavedTabsListAdapter());
        return v;
    }

    public class SavedTabsListAdapter extends BaseExpandableListAdapter {


        AdviceData varijable = new AdviceData();
        private String[][] children = varijable.getChildrenData();
        private String[] groups = varijable.getGroupsData();
        private Context _context;





        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return children[i].length;
        }

        @Override
        public Object getGroup(int i) {
            return groups[i];
        }

        @Override
        public Object getChild(int i, int i1) {
            return children[i][i1];
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            /*TextView textView = new TextView(SavedTabsFragment.this.getActivity());
            textView.setText(getGroup(i).toString());*/
            final String groupText = (String) getGroup(i);
            ImageView arrowUp, arrowDown;
            if(view == null){
                LayoutInflater infalInflater1 = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = infalInflater1.inflate(R.layout.advice_group_view, null);
            }

            arrowUp = (ImageView) view.findViewById(R.id.arrow_up_list);
            arrowDown = (ImageView) view.findViewById(R.id.arrow_down_list);

            if(b)
            {

                view.setBackgroundColor(getResources().getColor(R.color.main_green_color));

                arrowDown.setVisibility(View.INVISIBLE);
                arrowUp.setVisibility(View.VISIBLE);

            }
            else
            {
                view.setBackgroundColor(getResources().getColor(R.color.white_color));

                arrowDown.setVisibility(View.VISIBLE);
                arrowUp.setVisibility(View.INVISIBLE);
            }


            TextView txtListGroup = (TextView) view.findViewById(R.id.group_text_view);
            txtListGroup.setText(groupText);

            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            /*TextView textView = new TextView(SavedTabsFragment.this.getActivity());
            textView.setText(getChild(i, i1).toString());
            textView.setPadding(10,0,0,0);*/

            final String childText = (String) getChild(i,i1);

            if (view == null) {
                LayoutInflater infalInflater2 = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = infalInflater2.inflate(R.layout.advice_child_view, null);
            }

            TextView txtListChild = (TextView) view
                    .findViewById(R.id.child_text_view);

            txtListChild.setText(childText);
            return view;

        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

    }


}