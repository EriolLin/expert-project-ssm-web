package com.backoffice.mapper;

import com.backoffice.model.SubjectGroupCandidate;
import com.backoffice.model.SubjectGroupCandidateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubjectGroupCandidateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    long countByExample(SubjectGroupCandidateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    int deleteByExample(SubjectGroupCandidateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    int insert(SubjectGroupCandidate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    int insertSelective(SubjectGroupCandidate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    List<SubjectGroupCandidate> selectByExample(SubjectGroupCandidateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    SubjectGroupCandidate selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    int updateByExampleSelective(@Param("record") SubjectGroupCandidate record, @Param("example") SubjectGroupCandidateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    int updateByExample(@Param("record") SubjectGroupCandidate record, @Param("example") SubjectGroupCandidateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    int updateByPrimaryKeySelective(SubjectGroupCandidate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject_group_candidate
     *
     * @mbg.generated Sat Apr 06 14:54:33 CST 2019
     */
    int updateByPrimaryKey(SubjectGroupCandidate record);
}