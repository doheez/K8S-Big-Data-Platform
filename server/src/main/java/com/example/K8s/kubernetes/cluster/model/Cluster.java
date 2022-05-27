package com.example.K8s.kubernetes.cluster.model;

import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.web.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cluster")
public class Cluster extends TimeStamped{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int amount;

    // type 0 - hadoop
    // type 1 - spark
    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private String namespace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 연관관계 편의 메서드
    public void setUser(User user) {
        if (this.user != null) {
            this.user.removeCluster(this);
        }
        this.user = user;
        user.addCluster(this);
    }
  

  
    @OneToMany(mappedBy="cluster")
    private List<Spark> sparks;

    public void addSpark(Spark spark){
        this.sparks.add(spark);
    }
    public void removeSpark(Spark spark){
        this.sparks.remove(spark);
    }

  

    @OneToMany(mappedBy = "hadoopCluster")
    private List<Hadoop> hadoops;

    // 연관관계 편의 메서드
    public void addHadoop(Hadoop hadoop) {
        this.hadoops.add(hadoop);
    }
    public void removeHadoop(Hadoop hadoop) {
        this.hadoops.remove(hadoop);
    }



    public Cluster(ClusterRegDto regDto, User user) {
        this.name = regDto.getName();
        this.amount = regDto.getAmount();
        this.type = regDto.getType();
        setUser(user);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setNamespace(String namespace){
        this.namespace = namespace;
    }
}
