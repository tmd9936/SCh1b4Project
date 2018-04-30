
package com.h1b4.www.youtube.information;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.h1b4.www.vo.Contents;

/**
 * getVideoData함수를 갖고있다
 * @author SCITMASTER
 *
 */
public class DataExtractor {

	public DataExtractor() {
	}
	
	/**
	 * 유튜브의 데이터를 얻는다.
	 * @param videoUrl
	 * @return contents를 리턴
	 * @throws IOException
	 */
	public Contents getVideoData(String videoUrl, String loginId) throws IOException {

		Document doc = Jsoup.connect(videoUrl).header("User-Agent", "Chrome").get();

		Elements script = doc.select("script");
		// System.out.println(script.html());
		Pattern p = Pattern.compile("len=[0-9]+");
		// Pattern p = Pattern.compile("\"length_seconds\":\"(.+?)\"");
		Matcher m = p.matcher(script.html());
		m.find();
		String dur = m.group().substring(4);
		if (Integer.parseInt(dur) > 20000) {
			p = Pattern.compile("\"length_seconds\":\"(.+?)\"");
			m = p.matcher(script.html());
			m.find();
			dur = m.group().substring(17, m.group().length() - 1);
		}
		
		dur = dur.replace("\"", "");
		int t = Integer.parseInt(dur) / 3600;
		int bun = (Integer.parseInt(dur) % 3600) / 60;
		int cho = (Integer.parseInt(dur) % 3600) % 60;

		dur = t + ":" + bun + ":" + cho;

		Element body = doc.body();

		String videoThumbnail = body.getElementsByAttributeValue("itemprop", "thumbnailUrl").get(0).attr("href");
		String videoEmbedUrl = body.getElementsByAttributeValue("itemprop", "embedURL").get(0).attr("href");
		String videoTitle = body.getElementById("eow-title").attr("title");
		/*
		 * String userLink = body.getElementById("watch7-user-header")
		 * .getElementsByAttributeValue("class",
		 * "yt-user-photo yt-uix-sessionlink      spf-link").attr("href"); String
		 * userPhoto =
		 * body.getElementById("watch7-user-header").getElementsByTag("img").attr(
		 * "data-thumb"); String channelLink =
		 * body.getElementById("watch7-user-header").getElementsByClass("yt-user-info").
		 * get(0) .child(0).attr("href"); String channelName =
		 * body.getElementById("watch7-user-header").getElementsByClass("yt-user-info").
		 * get(0) .child(0).wholeText(); boolean isChannelVerified; try {
		 * isChannelVerified =
		 * body.getElementById("watch7-user-header").getElementsByClass("yt-user-info").
		 * get(0) .child(1).attr("aria-label").equalsIgnoreCase("Verified") ? true :
		 * false; } catch (Exception e) { isChannelVerified = false; }
		 */
		/*
		 * String noOfSubs = body
		 * .getElementsByClass("yt-subscription-button-subscriber-count-branded-horizontal yt-subscriber-count"
		 * ) .attr("title");
		 */
		// String viewCount = body.getElementsByClass("watch-view-count").text();
		/*
		 * String noOfLikes = body.getElementsByAttributeValue("title",
		 * "I like this").get(0).text(); String noOfDislikes =
		 * body.getElementsByAttributeValue("title", "I dislike this").get(0).text();
		 */
		// String publishedOn =
		// body.getElementById("watch-uploader-info").text().replace("Published on ",
		// "");
		// String description =
		// body.getElementById("watch-description-text").children().text();

		boolean isFamilyFriendly = body.getElementsByAttributeValue("itemprop", "isFamilyFriendly").attr("content")
				.equalsIgnoreCase("True") ? true : false;
		if (!isFamilyFriendly) {
			// 19금이면 null 리턴
			return null;
		}

		String genre = body.getElementsByAttributeValue("itemprop", "genre").attr("content");
		// VideoData videoData = new VideoData(videoDuration,
		// videoThumbnail,videoEmbedUrl,videoTitle,userLink,userPhoto,channelLink,channelName,isChannelVerified,noOfSubs,viewCount,/*noOfLikes,noOfDislikes,*/publishedOn,description,isFamilyFriendly,genre);
		Contents contents = new Contents();
		contents.setContents_title(videoTitle);
		contents.setCategory(genre);
		contents.setContents_url(videoEmbedUrl);
		contents.setThumbnail(videoThumbnail);
		contents.setEndtime(dur);
		contents.setDeletestate("no");
		contents.setMember_id(loginId);

		return contents;
	}
}
