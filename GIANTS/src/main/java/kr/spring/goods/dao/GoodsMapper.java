package kr.spring.goods.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.goods.vo.GoodsFavVO;
import kr.spring.goods.vo.GoodsOptionVO;
import kr.spring.goods.vo.GoodsQnaVO;
import kr.spring.goods.vo.GoodsReviewVO;
import kr.spring.goods.vo.GoodsVO;

@Mapper
public interface GoodsMapper {
	//=====일반 상품=====//
	//전체|검색 상품 목록
	public List<GoodsVO> selectGoodsList(Map<String, Object> map);
	//전체|검색 상품 레코드수
	public int selectGoodsRowCount(Map<String, Object> map);
	
	//상품 정보 등록 
	@Select("SELECT goods_seq.nextval FROM dual")
	public int selectGoodsNum();
	public void insertGoods(GoodsVO goodsVO);
	//상품 재고 등록
	public void insertGoodsOption(@Param(value="goods_num") Integer goods_num, 
								@Param(value="goods_size") String goods_size, 
								@Param(value="goods_stock") Integer goods_stock);
	
	//상품 상세
	@Select("SELECT * FROM goods WHERE goods_num=#{goods_num}")
	public GoodsVO selectGoods(Integer goods_num);
	//상품 재고 목록
	public List<GoodsOptionVO> selectOptionList(Integer goods_num);
	 
	//상품 정보 수정
	public void updateGoods(GoodsVO goods);
	//상품 재고 수정
	public void updateOption(@Param(value="goods_num") Integer goods_num, 
							@Param(value="goods_size") String goods_size, 
							@Param(value="goods_stock") Integer goods_stock);
	//상품 삭제
	@Delete("DELETE FROM goods WHERE goods_num=#{goods_num}")
	public void deleteGoods(Integer goods_num);
	//상품 옵션 삭제
	@Delete("DELETE FROM goods_option WHERE goods_num=#{goods_num}")
	public void deleteOption(Integer goods_num);
	
	//=====상품 찜=====//
	//상품 찜 정보 읽어오기
	@Select("SELECT * FROM goods_fav WHERE goods_num=#{goods_num} AND mem_num=#{mem_num}")
	public GoodsFavVO selectGoodsFav(GoodsFavVO fav);
	//상품 찜 레코드 수
	@Select("SELECT count(*) FROM goods_fav WHERE goods_num=#{goods_num}")
	public int selectGoodsFavCount(Integer goods_num);
	//상품 찜 등록
	@Insert("INSERT INTO goods_fav(fav_num, goods_num, mem_num) VALUES(goods_fav_seq.nextval, #{goods_num}, #{mem_num})")
	public void insertGoodsFav(GoodsFavVO fav);
	//상품 찜 취소
	@Delete("DELETE FROM goods_fav WHERE fav_num=#{fav_num}")
	public void deleteGoodsFav(Integer fav_num);
	//상품삭제시 상품 찜 취소
	public void deleteGoodsFavByGoodsNum(Integer goods_num);
	
	//=====상품 후기=====//
	//상품 후기 목록
	public List<GoodsReviewVO> selectGoodsReviewList(Map<String, Object> map);
	//상품 후기 레코드 수
	public int selectGreviewRowCount(Integer goods_num);
	//상품 후기 등록
	public void insertGoodReview(GoodsReviewVO review);
	//상품 후기 상세
	public GoodsReviewVO selectGoodsReview(Integer review_num);
	//상품 후기 수정
	public void updateGoodsReview(GoodsReviewVO review);
	//상품 후기 삭제
	public void deleteGoodsReview(Integer review_num);
	
	//평균 별점 표시
	@Select("SELECT NVL(avg(review_score),0) FROM goods_review WHERE goods_num=#{goods_num}")
	public int getAvgScore(Integer goods_num);
	
	//=====상품 문의=====//
	//전체 | 검색 상품 문의 목록
	public List<GoodsQnaVO> selectGoodsQnaList(Map<String, Object> map);
	//전체 | 검색 상품 문의 레코드 수
	@Select("SELECT count(*) FROM goods_qna WHERE goods_num=#{goods_num}")
	public int selectGoodsQnaCount(Integer goods_num);
	//상품 문의 등록
	public void insertGoodsQna(GoodsQnaVO qna);
	//상품 문의 상세
	public GoodsQnaVO selectQna(Integer qna_num);
	//상품 문의 수정
	//상품 문의 삭제
}
