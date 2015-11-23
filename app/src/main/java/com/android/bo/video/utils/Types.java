package com.android.bo.video.utils;

import java.util.ArrayList;

/*
 * Created by Bo on 21.11.2015.
 */
public class Types {

    public enum Tabs {
        Ukraine,
        Russian,
        All,
        Favourite
    }

    public static ArrayList<Uris> UrkaineChannels = new ArrayList<Uris>() {
        {
            add(Uris.Triolan1);
            add(Uris.Triolan2);
            add(Uris.UkraineTV);
        }
    };

    public static ArrayList<Uris> RussainChannels = new ArrayList<Uris>() {
        {
            add(Uris.RussianTv);
            add(Uris.RusTelecom);
            add(Uris.ZabavaSlyUkraine);
            add(Uris.Denms);
        }
    };

    public static ArrayList<Uris> AllChannels = new ArrayList<Uris>() {
        {
            add(Uris.KamikadzeAll);
            add(Uris.Triolan1);
            add(Uris.Triolan2);
            add(Uris.RussianTv);
            add(Uris.UkraineTV);
            add(Uris.RusTelecom);
            add(Uris.ZabavaSlyUkraine);
            add(Uris.Denms);
        }
    };

    public enum Uris {
        KamikadzeAll("http://iptv56.ru/tvtest.m3u", 0, 0, "\r\n", ""),
        Triolan1("http://www.satorbita.com/iptv/free/free4triolan_test.php?list.m3u", 5, 0, "\n", "(Premium)"),
        Triolan2("http://iptvworld.pp.ua/iptv/free/free3triolan_test.php?list.m3u", 5, 0, "\n", "(Premium)"),
        RussianTv("http://545-tv.com/listRU.php?m3u", 2, 2, "\r\n", "[Premium]"),
        UkraineTV("http://545-tv.com/listUA.php?m3u", 2, 2, "\r\n", "[Premium]"),
        RusTelecom("http://www.satorbita.com/iptv/free/free5novosibirsk.php?list.m3u", 5, 0, "\n", "(Premium)"),
        ZabavaSlyUkraine("http://slynet.pw/ZabavaSlyUkraine.m3u", 2, 0, "\r\n", "="),
        Denms("http://iptv.denms.ru/iptv-rt0.m3u", 0, 0, "\r\n", "(Premium)");


        private String uri;
        private int startIndex;
        private int endIndex;
        private String separator;
        private String ignorePremium;

        Uris(String uri, int startIndex, int endIndex, String separator, String ignorePremium) {
            this.uri = uri;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.separator = separator;
            this.ignorePremium = ignorePremium;
        }

        public String getUri() {
            return uri;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public String getSeparator() {
            return separator;
        }

        public String getIgnorePremium() {
            return ignorePremium;
        }
    }
}
