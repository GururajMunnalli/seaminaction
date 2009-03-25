package org.example.tssjs.model

import javax.persistence.*

@Entity
@Table(name = "user_account")
class UserAccount implements Serializable {

	@Id @GeneratedValue	Long id
	String username
	String password

}
