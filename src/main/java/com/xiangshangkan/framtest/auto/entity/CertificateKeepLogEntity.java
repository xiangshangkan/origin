package com.xiangshangkan.framtest.auto.entity;

import java.io.Serializable;
import java.util.Date;

public class CertificateKeepLogEntity implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column certificate_keep_log.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column certificate_keep_log.certificate_id
     *
     * @mbggenerated
     */
    private Integer certificateId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column certificate_keep_log.start_keep_time
     *
     * @mbggenerated
     */
    private Date startKeepTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column certificate_keep_log.end_keep_time
     *
     * @mbggenerated
     */
    private Date endKeepTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column certificate_keep_log.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column certificate_keep_log.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column certificate_keep_log.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table certificate_keep_log
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column certificate_keep_log.id
     *
     * @return the value of certificate_keep_log.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column certificate_keep_log.id
     *
     * @param id the value for certificate_keep_log.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column certificate_keep_log.certificate_id
     *
     * @return the value of certificate_keep_log.certificate_id
     *
     * @mbggenerated
     */
    public Integer getCertificateId() {
        return certificateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column certificate_keep_log.certificate_id
     *
     * @param certificateId the value for certificate_keep_log.certificate_id
     *
     * @mbggenerated
     */
    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column certificate_keep_log.start_keep_time
     *
     * @return the value of certificate_keep_log.start_keep_time
     *
     * @mbggenerated
     */
    public Date getStartKeepTime() {
        return startKeepTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column certificate_keep_log.start_keep_time
     *
     * @param startKeepTime the value for certificate_keep_log.start_keep_time
     *
     * @mbggenerated
     */
    public void setStartKeepTime(Date startKeepTime) {
        this.startKeepTime = startKeepTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column certificate_keep_log.end_keep_time
     *
     * @return the value of certificate_keep_log.end_keep_time
     *
     * @mbggenerated
     */
    public Date getEndKeepTime() {
        return endKeepTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column certificate_keep_log.end_keep_time
     *
     * @param endKeepTime the value for certificate_keep_log.end_keep_time
     *
     * @mbggenerated
     */
    public void setEndKeepTime(Date endKeepTime) {
        this.endKeepTime = endKeepTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column certificate_keep_log.create_time
     *
     * @return the value of certificate_keep_log.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column certificate_keep_log.create_time
     *
     * @param createTime the value for certificate_keep_log.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column certificate_keep_log.update_time
     *
     * @return the value of certificate_keep_log.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column certificate_keep_log.update_time
     *
     * @param updateTime the value for certificate_keep_log.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column certificate_keep_log.status
     *
     * @return the value of certificate_keep_log.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column certificate_keep_log.status
     *
     * @param status the value for certificate_keep_log.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table certificate_keep_log
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", certificateId=").append(certificateId);
        sb.append(", startKeepTime=").append(startKeepTime);
        sb.append(", endKeepTime=").append(endKeepTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table certificate_keep_log
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CertificateKeepLogEntity other = (CertificateKeepLogEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCertificateId() == null ? other.getCertificateId() == null : this.getCertificateId().equals(other.getCertificateId()))
            && (this.getStartKeepTime() == null ? other.getStartKeepTime() == null : this.getStartKeepTime().equals(other.getStartKeepTime()))
            && (this.getEndKeepTime() == null ? other.getEndKeepTime() == null : this.getEndKeepTime().equals(other.getEndKeepTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table certificate_keep_log
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCertificateId() == null) ? 0 : getCertificateId().hashCode());
        result = prime * result + ((getStartKeepTime() == null) ? 0 : getStartKeepTime().hashCode());
        result = prime * result + ((getEndKeepTime() == null) ? 0 : getEndKeepTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }
}