package com.ytf.api.demos;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.*;

public class AndroidApiDemos extends ListActivity {

    /**
     * sort the demos by title
     */
    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                final Collator collator = Collator.getInstance();

                @Override
                public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
                    return collator.compare(lhs.get("title"), rhs.get("title"));
                }
            };

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String path = intent.getStringExtra("com.example.android.apis.Path");

        if (path == null) {
            path = "";
        }

        setListAdapter(new SimpleAdapter(this, getData(path), android.R.layout.simple_list_item_1,
                new String[]{"title"}, new int[]{android.R.id.text1}));
        getListView().setTextFilterEnabled(true);
    }

    /**
     * @param prefix
     * @return
     */
    protected List<Map<String, Object>> getData(String prefix) {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_SAMPLE_CODE);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list) {
            return myData;
        }

        String[] prefixPath;
        String prefixWithSlash = prefix;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }

        int len = list.size();
        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; ++i) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString() : info.activityInfo.name;

            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {
                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, activityIntent(info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        Collections.sort(myData, sDisplayNameComparator);
        return myData;
    }

    /**
     * @param path
     * @return
     */
    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, AndroidApiDemos.class);
        result.putExtra("com.example.android.apis.Path", path);
        return result;
    }

    /**
     * @param data
     * @param name
     * @param intent
     */
    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    /**
     * @param pkg
     * @param componentName
     * @return
     */
    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    /**
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);

        Intent intent = new Intent((Intent) map.get("intent"));
        intent.addCategory(Intent.CATEGORY_SAMPLE_CODE);

        startActivity(intent);
    }
}
