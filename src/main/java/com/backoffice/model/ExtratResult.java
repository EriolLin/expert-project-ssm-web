package com.backoffice.model;

import java.util.Date;

public class ExtratResult {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column extrat_result.id
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column extrat_result.year
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    private Date year;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column extrat_result.subject_id
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    private Integer subjectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column extrat_result.leader
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    private String leader;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column extrat_result.deputy_leader
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    private String deputyLeader;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column extrat_result.member
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    private String member;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column extrat_result.id
     *
     * @return the value of extrat_result.id
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column extrat_result.id
     *
     * @param id the value for extrat_result.id
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column extrat_result.year
     *
     * @return the value of extrat_result.year
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public Date getYear() {
        return year;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column extrat_result.year
     *
     * @param year the value for extrat_result.year
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public void setYear(Date year) {
        this.year = year;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column extrat_result.subject_id
     *
     * @return the value of extrat_result.subject_id
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column extrat_result.subject_id
     *
     * @param subjectId the value for extrat_result.subject_id
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column extrat_result.leader
     *
     * @return the value of extrat_result.leader
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public String getLeader() {
        return leader;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column extrat_result.leader
     *
     * @param leader the value for extrat_result.leader
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column extrat_result.deputy_leader
     *
     * @return the value of extrat_result.deputy_leader
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public String getDeputyLeader() {
        return deputyLeader;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column extrat_result.deputy_leader
     *
     * @param deputyLeader the value for extrat_result.deputy_leader
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public void setDeputyLeader(String deputyLeader) {
        this.deputyLeader = deputyLeader == null ? null : deputyLeader.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column extrat_result.member
     *
     * @return the value of extrat_result.member
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public String getMember() {
        return member;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column extrat_result.member
     *
     * @param member the value for extrat_result.member
     *
     * @mbg.generated Tue Apr 02 21:20:45 CST 2019
     */
    public void setMember(String member) {
        this.member = member == null ? null : member.trim();
    }

	@Override
	public String toString() {
		return "ExtratResult [id=" + id + ", year=" + year + ", subjectId=" + subjectId + ", leader=" + leader
				+ ", deputyLeader=" + deputyLeader + ", member=" + member + "]";
	}
    
}