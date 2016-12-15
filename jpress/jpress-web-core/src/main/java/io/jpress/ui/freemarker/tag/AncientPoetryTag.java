package io.jpress.ui.freemarker.tag;

import io.jpress.core.render.freemarker.JTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by fjw on 12/10/16.
 */
public class AncientPoetryTag extends JTag {

    public static final String TAG_NAME = "jp.ancientpoetry";
    private static AncientPoetryTag mAncientPoetryTag;

    List<An> list = new ArrayList<>();

    public static AncientPoetryTag getAn() {
        if (mAncientPoetryTag == null) {
            mAncientPoetryTag = new AncientPoetryTag();
        }
        return mAncientPoetryTag;
    }

    @Override
    public void onRender() {
        if (list.isEmpty()) {
            init();
        }
        setVariable("an", list.get(new Random().nextInt(list.size() - 1)));
        renderBody();
    }

    public void init() {
        list.add(new An("幸有我来山未孤。", "丰子恺"));
        list.add(new An("掬水月在手，弄花香满衣。", "于良史"));
        list.add(new An("留得残荷听雨声。", "李商隐"));
        list.add(new An("惜春更把残红折。", "张先"));
        list.add(new An("已识乾坤大，犹怜草木青。", "马一浮"));
        list.add(new An("一蓑烟雨任平生。", "苏轼"));
        list.add(new An("诗酒趁年华。", "苏轼"));
        list.add(new An("人间有味是清欢。", "苏轼"));
        list.add(new An("但愿人长久，千里共婵娟。", "苏轼"));
        list.add(new An("行到水穷处，坐看云起时。", "王维"));
        list.add(new An("小楼吹彻玉笙寒。", "李璟"));
        list.add(new An("惟梦闲人不梦君。", "元稹"));
        list.add(new An("但见新人笑，那闻旧人哭。", "杜甫"));
        list.add(new An("晚来天欲雪，能饮一杯无。", "白居易"));
        list.add(new An("秋风生渭水，落叶满长安。", "贾岛"));
        list.add(new An("天若有情天亦老。", "李贺"));
        list.add(new An("青青子衿，悠悠我心。", "诗经"));
        list.add(new An("所谓伊人，在水一方。", "诗经"));
        list.add(new An("一庭疏雨湿春愁。", "孙光宪"));
        list.add(new An("雨后却斜阳，杏花零落香。", "温庭筠"));
        list.add(new An("幽窗棋罢指犹凉。", "红楼梦"));
        list.add(new An("烟霞闲骨骼，泉石野生涯。", "红楼梦"));
//        list.add(new An("却道天凉好个秋"));
        list.add(new An("人生天地间，忽如远行客。", "古诗十九首"));
        list.add(new An("当时只道是寻常。", "纳兰性德"));
        list.add(new An("人生若只如初见。", "纳兰性德"));
        list.add(new An("愿得一心人，白首不相离。", "卓文君"));
//        list.add(new An("举头望明月，低头思故乡。"));
        list.add(new An("生活在别处。", "兰波"));
//        list.add(new An("冬天来了，春天还会远吗？雪莱"));
        list.add(new An("面朝大海，春暖花开。", "海子"));
        list.add(new An("远方除了遥远一无所有。", "海子。"));
        list.add(new An("人时已尽，人世还长。", "顾城"));
        list.add(new An("悲伤使人格外敏锐。", "罗曼·罗兰。"));
        list.add(new An("我常以为是众生度化了佛祖。", "史铁生"));
        list.add(new An("我已亭亭，不忧，亦不惧。", "席慕蓉"));
        list.add(new An("明朝即长路，惜取此时心。", "钱钟书"));
        list.add(new An("绝望是无限的美好。", "王小波"));
        list.add(new An("相信未来，热爱生命。", "食指"));
        list.add(new An("你还不来，我怎敢老去。", "张爱玲"));
        list.add(new An("对于信徒，死既永生。", "弥尔顿。"));
        list.add(new An("白昼之光，岂知夜色之深。", "尼采"));
        list.add(new An("临渊羡鱼，不如退而结网。", "《汉书》"));
        list.add(new An("当断不断，反受其乱。", "《汉书》"));
        list.add(new An("前车之覆，后车之鉴。", "《汉书》"));
        list.add(new An("失之毫厘，谬以千里。", "《汉书》"));
        list.add(new An("狐疑犹豫，后必有悔。", "《汉书》"));
        list.add(new An("厉精图治，将有大为。", "《汉书》"));
        list.add(new An("仁不异远，义不辞难。", "《汉书》"));
        list.add(new An("夫众煦漂山，聚蚊成雷。", "《汉书》"));
        list.add(new An("人若无志，与禽兽同类。", "《孟子》"));
        list.add(new An("虽有智慧，不如乘势；虽有镃基，不如待时。", "孟子"));
        list.add(new An("君子藏器于身，待时而动。", "《周易》"));
        list.add(new An("不烦不扰，淡泊不失。", "《周易》"));
        list.add(new An("谦谦君子，卑以自牧。", "《周易》"));
        list.add(new An("尺蠖之屈，以求信也；龙蛇之蛰，以存身也。", "《周易》"));
        list.add(new An("面朝大海 春暖花开", "（海子）"));
        list.add(new An(" 虽千万人吾往矣 ", "（孟子）"));
        list.add(new An("春风十里 不如你 ", "（冯唐）"));
        list.add(new An("陌上花开 可缓缓归矣", "（钱武肃王）"));
        list.add(new An("当时明月在 曾照彩云归", "（晏几道）"));
        list.add(new An("落花人独立 微雨燕双飞", "（晏几道）"));
        list.add(new An("当时只道是寻常 ", "（纳兰容若）"));
        list.add(new An("提笼忘采叶 昨夜梦渔阳 ", "（张仲素）"));
        list.add(new An("啼时惊妾梦 不得到辽西 ", "（金昌绪）"));
        list.add(new An("所嗟人异雁 不作一行飞 ", "（七岁女）"));
        list.add(new An("月上柳梢头 人约黄昏后 ", "（欧阳修）"));
        list.add(new An(" 林花谢了春红 太匆匆 ", "（李煜）"));
        list.add(new An("翩若惊鸿 宛若游龙 ", "（曹植）"));
        list.add(new An("青青子衿 悠悠我心 ", "（诗经）"));
        list.add(new An("情不知所起 一往而深", "（汤显祖）"));
        list.add(new An("一见如故 再见陌路", "（仓央嘉措）"));
        list.add(new An("月出皎兮 佼人僚兮", "（诗经）"));
        list.add(new An("不知魂已断 空有梦相随", "（韦庄）"));
        list.add(new An("留得枯荷听雨声", "（李商隐）"));
        list.add(new An("桃之夭夭 灼灼其华", "（诗经）"));
        list.add(new An("巧笑倩兮 美目盼兮", "（诗经）"));
//        list.add(new An("我曾拥有你 想到就心酸"));
//        list.add(new An("酒暖回忆思念瘦"));
        list.add(new An("遗憾和努力 哪个更痛苦", "（卡尔特人队训）"));
        list.add(new An("但盼风雨来 能留你在此", "（言叶之亭）"));
//        list.add(new An("弱水三千 只取一瓢"));
        list.add(new An("对酒当歌 人生几何", "（曹操）"));
        list.add(new An("愿得一人心 白首不相离", "（卓文君）"));
        list.add(new An("结发为夫妻 恩爱两不移", "（苏武）"));
//        list.add(new An(" 我会记得你 然后爱别人"));
//        list.add(new An("喜你为疾 药石无医"));
//        list.add(new An("本来无一物 何处惹尘埃"));
//        list.add(new An("一花一世界 一草一菩提"));
        list.add(new An("心似双丝网 中有千千结", "（张先）"));
        list.add(new An("红了樱桃 绿了芭蕉", "（蒋捷）"));
        list.add(new An("无为在歧路 儿女共沾巾", "（王勃）"));
        list.add(new An("坐观垂钓者 徒有羡渔情", "（孟浩然）"));
        list.add(new An("记得绿罗裙 处处怜芳草", "（贺铸）"));
//        list.add(new An("低头弄莲子 莲", "（怜）子清", "（情）如水 ", "（西洲曲）"));
        list.add(new An("斯人若彩虹 遇上方知有", "（怦然心动 韩寒译）"));
        list.add(new An("我的征途是星辰大海", "（今何在 书名）"));
        list.add(new An("总有人会赢，为何不是我", "（科比）"));
        list.add(new An("世界这么大 我想去看看", "（最牛辞职信）"));
        list.add(new An("你若安好 便是晴天", "（林徽因）"));
//        list.add(new An("择一城终老 遇一人白首"));
//        list.add(new An("执子之手 与子偕老"));
        list.add(new An("青春是道明媚的忧伤", "（郭敬明）"));
        list.add(new An("让我感谢你 赠我空欢喜", "（林夕 花事了）"));
        list.add(new An("不介意孤独 比爱你舒服", "（林夕 献世）"));
        list.add(new An("只是喜欢 何必夸张成爱", "（林夕）"));
        list.add(new An("余生摇摇，天命昭昭熊培云", "《我是即将来到的日子》"));
        list.add(new An("南风知我意，吹梦到西洲", "《西洲曲》"));
        list.add(new An("与君远相知，不道云海深王昌龄", "《寄欢州》"));
        list.add(new An("白头如新，倾盖如故汉·邹阳", "《狱中上梁王书》"));
        list.add(new An("匹夫无罪，怀璧其罪", "《春秋左传·桓公十年》"));
        list.add(new An("四时常相往，晴日共剪窗民谣", "《晴日共剪窗》"));
        list.add(new An("人来人往，只是日常蔡康永离开", "《康熙来了》时说的话"));
//        list.add(new An("持心如衡，以理为平刘基"));
        list.add(new An("为你，千千万万遍", "《追风筝的人》"));
        list.add(new An("故乡飘已远，往意浩无边苏轼", "《初发嘉州》"));
        list.add(new An("我习于冷，志于成冰木心", "《大卫》"));
//        list.add(new An("花开如火，也如寂寞顾城"));
//        list.add(new An("人可生而如蚁而美如神顾城"));
//        list.add(new An("生命与生活无关顾城"));
        list.add(new An("还将旧来意，怜取眼前人元稹", "《莺莺传》"));
//        list.add(new An("人间草木深，我心桃花源"));
        list.add(new An("人闲桂花落，夜静春山空王维", "《鸟鸣涧》"));
        list.add(new An("人生如逆旅，我亦是行人苏轼", "《临江仙 送钱穆父》"));
        list.add(new An("南有乔木，不可休思", "《诗经·周南·汉广》"));
//        list.add(new An("我醉欲眠，卿可去陶渊明"));
        list.add(new An("虽千万人吾往矣孟子 ", "《公孙丑上》"));
        list.add(new An("凄凄岁暮风，翳翳经日雪陶渊明", "《癸卯岁十二月中作与从弟敬远》"));
//        list.add(new An("古路无行客，寒山独见君。", "刘长卿的", "《碧涧别墅喜皇甫侍御相访》"));
        list.add(new An("以梦为鹿,亡与桎梏", "《列子﹒周穆王》"));
        list.add(new An("涧松寒转直，山菊秋自香王绩", "《赠李征君大寿》"));
        list.add(new An("岂无他人，念子实多陶渊明", "《停云》"));
    }


    public class An {
        private String sentence;
        private String author;

        public An(String sentence, String author) {
            this.sentence = sentence;
            this.author = author;
        }

        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
//
//君子上交不诌，下交不渎。","
//
//","《周易》
//
//
//君子以言有物，而行有恒。","
//
//","《周易》
//
//
//心和生则种种法生，心灭则种种法灭
//
//","《大乘起信论》
//
//
//天地不仁，以万物为刍狗；
//圣人不仁，以百姓为刍狗。","
//
//","《道德经》
//
//
//信言不美。","美言不信。","
//善者不辩。","辩者不善。","
//知者不博。","博者不知。","
//
//","《道德经》
//
//
//大方无隅，大器晚成。","
//大音希声，大象无形。","
//
//","《道德经》
//
//
//夫轻诺必寡信，多易必多难。","
//
//","《道德经》
//
//
//慎终如始，则无败事。","
//
//","《道德经》
//
//
//知人者智，自知者明。","
//
//","《道德经》
//
//
//天道无亲，常与善人。","
//
//","《道德经》
//
//
//见素抱朴，少私寡欲。","
//
//","《道德经》
//
//
//自伐者无功,自矜者不长。","
//
//","《老子》
//
//
//故常无欲，以观其妙。","
//
//","《道德经》
//
//
//右手画圆，左手画方，不能两全。","
//
//","《韩非子》
//
//
//至乐无乐，至誉无誉。","
//
//","《庄子》
//
//
//夏虫不可以语冰。","
//
//","《庄子》
//
//
//人生在世，恍若白驹过隙，忽然而已。","
//
//","《庄子》
//
//
//饱食而遨游，泛若不系之舟。","
//
//","《庄子》
//
//
//天道酬勤，厚德载物
//
//","《尚书》
//
//
//知之非艰，行之惟艰。","
//
//","《尚书》
//
//
//君子不自大其事，不自尚其功。","
//
//","《礼记》
//
//
//恶言不出于口，忿言不反于身。","
//
//","《礼记》
//
//
//有其言，无其行，君子耻之。","
//
//","《礼记》
//
//
//爱而知其恶，憎而知其善。","
//
//","《礼记》
//
//学，然后知不足。","
//
//","《礼记》
//
//
//蓬生麻中，不扶而直。","
//
//","《荀子》
//
//
//知而好问，然后能才。","
//
//","《荀子》
//
//
//
//―分割线―
//
//今夕何夕，见此良人。","
//
//","《诗经》
//
//
//高山仰止，景行行止。","
//虽不能至，心向往之。","
//
//","《诗经》
//
//
//纵我不往，子宁不嗣音？
//
//","《诗经 》
//
//
//式微，式微，胡不归。","
//
//","《诗经》
//
//
//琴瑟在御，莫不静好。","
//
//","《诗经》
//
//
//愿一日，有女同车，颜如舜华。","
//
//","《诗经》
//
//
//德音莫违，及尔同死。","
//
//","《诗经》
//
//
//他山之石，可以攻玉。","
//
//","《诗经》
//
//
//终温且惠，淑慎其身。","
//
//","《诗经》
//
//
//维以不永伤，维以不永怀。","
//
//","《诗经》
//
//
//絺兮绤兮，凄其以风。","
//我思古人，实获我心。","
//
//","《诗经 》
//
//
//知我如此，不如无生。","
//
//","《诗经》
//
//
//靡不有初，鲜克有终。","
//
//","《诗经》
//
//
//岂不尔思，子不我即。","
//
//","《诗经》
//
//
//凤皇于飞，翙翙其羽，亦傅于天。","
//","《诗经》
//
//
//投我以木瓜，报之以琼琚。","
//","《诗经》
//
//
//嘤其呜矣，求其友声 。","
//
//","《诗经》
//
//
//我瞻四方，蹙蹙靡所骋。","
//","《诗经》
//
//
//鸢飞戾天，鱼跃于渊。","
//","《诗经》
//
//
//谁谓荼苦，其甘如荠。","
//","《诗经》
//
//
//巧言如簧，颜之厚矣。","
//","《诗经》
//
//
//相鼠有皮，人而无仪；
//人而无仪，不死何为？
//","《诗经》
//
//
//人世之事，非人世所可尽。","
//
//汤显祖","《牡丹亭》
//
//
//智均不相使，力均不相胜。","
//
//","《申子》
//
//
//知之始已，自知而后知人也。","
//
//","《鬼谷子》
//
//
//诚以待物，物必应以诚。","
//
//","《宋史》
//
//
//独行不愧影，独寝不愧衾。","
//
//","《宋史》
//
//
//故仁莫大于爱人，智莫大于知人。","
//
//","《文子》
//
//
//宽而栗，严而温，柔而直，猛而仁。","
//
//","《文子》
//
//
//他人观花，不涉你目。","
//他人碌碌，不涉你足。","
//
//","《增广贤文》
//
//
//责人之心责己，恕己之心恕人。","
//
//","《增广贤文》
//
//
//群居守口,独坐防心。","
//
//","《增广贤文》
//
//
//近水知鱼性，近山识鸟音。","
//
//","《增广贤文》
//
//
//自恨枝无叶，莫怨太阳偏。","
//
//","《增广贤文》


}
