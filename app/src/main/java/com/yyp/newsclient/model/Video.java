package com.yyp.newsclient.model;

public class Video {
    public String backup_url_1;
    public int bitrate;
    public String definition;
    public String main_url;
    public int preload_interval;
    public int preload_max_step;
    public int preload_min_step;
    public int preload_size;
    public double size;
    public double socket_buffer;
    public int user_video_proxy;
    public int vheight;
    public String vtype;
    public int vwidth;

    @Override
    public String toString() {
        return "Video{" +
                "backup_url_1='" + backup_url_1 + '\'' +
                ", bitrate=" + bitrate +
                ", definition='" + definition + '\'' +
                ", main_url='" + main_url + '\'' +
                ", preload_interval=" + preload_interval +
                ", preload_max_step=" + preload_max_step +
                ", preload_min_step=" + preload_min_step +
                ", preload_size=" + preload_size +
                ", size=" + size +
                ", socket_buffer=" + socket_buffer +
                ", user_video_proxy=" + user_video_proxy +
                ", vheight=" + vheight +
                ", vtype='" + vtype + '\'' +
                ", vwidth=" + vwidth +
                '}';
    }
}
