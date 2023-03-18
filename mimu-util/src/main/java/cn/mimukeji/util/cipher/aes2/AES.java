/**
 * 编写者: 赖志环
 * 文件管理系统 --  AES算法类
 * 编写日期: 2007-10-19
 */
package cn.mimukeji.util.cipher.aes2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AES extends AESMap {
	/**
	 * 这是一个AES加密算法.
	 * @param filePath 你相加密的文件夹路径.
	 * @param savePath 保存加密后的文件路径
	 * @param strKey 用户密码.
	 * @param Nb 加密时明文的分组长度(32bits)
	 * @param Nk the 密码长度.
	 * @return 加密后数据            
	 */
	public long encryptFile(String filePath, String savePath, String strKey,
			int Nb, int Nk) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		int leave = 0;
		int length = 0;
		long copy_rounds = 0;

		try {
			/*以二进制读的方式打开要加密的文件*/
			fis = new FileInputStream(filePath);
			/*以二进制写的方式打开保存密文的文件*/
			fos = new FileOutputStream(savePath, true);
			/*得到要加密的文件的长度*/
			length = fis.available();

			if (length == 0)
				return 0;

			/*求剩余的字块的字节数*/
			leave = length % (4 * Nb);
			/*得到整块的加密轮数*/
			long rounds = length / (4 * Nb);

			if (leave != 0)
				rounds++;

			copy_rounds = rounds;
			/*作为加密时存放要加密的明文块*/
			byte[] state = new byte[4 * 8];
			/*用来进行短块处理时的缓存区*/
			byte[] copy = new byte[4 * 8];
			/*得到加密的轮数*/
			int Nr = GetRounds(Nb, Nk);
			/*生成各轮子密钥*/
			KeyExpansion(strKey, Nb, Nk, Nr);

			if (copy_rounds == 1 && rounds == 1) {

				if (leave == 0)
					fis.read(state, 0, 4 * Nb);//明文的长度恰好等于分组长度；
				else {
					fis.read(state, 0, leave);//明文的长度小于八个字符；
					for (int i = leave; i < 4 * Nb; i++)
						state[i] = 0; //后面用空格补齐；
				}

				state = Transform(ByteToChar(state), Nb, Nr); //加密变换；  
				fos.write(state, 0, 4 * Nb);//将加密后的密文块写入目标文件；
				rounds--;
			} else if (copy_rounds > 1 && leave != 0)//如果明文的长度大于分组长度且字符数不是分组长度的整数倍              
			{ //时，需要进行短块处理；
				fis.read(state, 0, 4 * Nb);
				state = Transform(ByteToChar(state), Nb, Nr);//先加密最前面的一块；
				fos.write(state, 0, leave);//仅将余数个字符存入文件，而将后部分密文
				//与后面的明文合在一起加密；
				int j = 0;
				for (int i = leave; i < 4 * Nb; i++)
					copy[j++] = state[i];
				fis.read(copy, j, leave);
				copy = Transform(ByteToChar(copy), Nb, Nr);
				fos.write(copy, 0, 4 * Nb);
				rounds -= 2;
			}
			while (rounds > 0)//以下处理的明文是分组的整数倍的情况；
			{
				fis.read(state, 0, 4 * Nb);
				state = Transform(ByteToChar(state), Nb, Nr);
				fos.write(state, 0, 4 * Nb);
				rounds--;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("文件夹没有找到!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("读写文件时,发生异常!");
		} finally {
			try {
				if (fis != null) {
					fis.close(); //关闭源文件
					fis = null;
				}
				if (fos != null) {
					fos.close();
					fos = null; //关闭目标文件
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("文件关闭出现异常!");
			}

		}

		return ((copy_rounds - 1) * 4 * Nb + leave);//返回文件长度；
	}

	/**
	 * 这个方法是用来解密密文的.
	 * @param OpenPath 密文路径.
	 * @param SavePath 解密后文件的保存路径.
	 * @param m_Key 解密文件的密钥.
	 * @param Nb 解密时密文的分组长度(32bits)
	 * @param Nk 密钥长度.
	 * @return 解密后的数据
	 */
	public long dencryptFile(String OpenPath, String SavePath, String m_Key,
			int Nb, int Nk) {
		//以二进制读的方式打开要加密的文件；
		//以二进制写的方式打开保存密文的文件；   
		FileInputStream fis = null;
		FileOutputStream fos = null;
		int Length = 0;
		long copy_rounds = 0;
		int leave = 0;
		
		try {
			fis = new FileInputStream(OpenPath);
			fos = new FileOutputStream(SavePath, true);
			Length = fis.available();
			
			//得到要加密的文件的长度；
			if (Length == 0)
				return 0;
			leave = Length % (4 * Nb);//求剩余的字块的字节数；
			long rounds = Length / (4 * Nb);//得到整块的加密轮数；
			if (leave != 0)
				rounds++;
			copy_rounds = rounds;

			byte[] state = new byte[4 * 8]; //解密时存放密文块；
			int Nr = GetRounds(Nb, Nk); //得到解密时循环轮数；
			KeyExpansion(m_Key, Nb, Nk, Nr); //生成各轮子密钥
			byte[] copy = new byte[32];

			if (leave != 0)//需要进行短块处理
			{
				fis.read(copy, 0, leave);//先把余数个密文字符保存；
				fis.read(state, 0, 4 * Nb);//读取紧接着的一个密文块；
				state = ReTransform(ByteToChar(state), Nb, Nr); //解密；
				int j = 0;
				for (int i = leave; i < 4 * Nb; i++)
					//把解密后的明文和前面的余数个合在一起组成一块，
					copy[i] = state[j++]; //一起解密；
				copy = ReTransform(ByteToChar(copy), Nb, Nr);
				//将解密后的明文写入目标文件；
				fos.write(copy, 0, 4 * Nb);
				fos.write(state, j, leave);//将余数个明文写入目标文件；
				rounds -= 2; //已经完成了两轮解密所以减二；
			}
			while (rounds > 0)//对后面是分组长度的整数倍的密文块解密；
			{
				fis.read(state, 0, 4 * Nb);//读取密文块；
				copy = ReTransform(ByteToChar(state), Nb, Nr); //解密变换；
				fos.write(copy, 0, 4 * Nb);//将解密后的明文写入目标文件；
				rounds--; //轮数减一；
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close(); //关闭源文件
					fis = null;
				}
				if (fos != null) {
					fos.close();
					fos = null; //关闭目标文件
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("文件关闭出现异常!");
			}

		}
		
		return ((copy_rounds - 1) * 4 * Nb + leave);//返回文件长度
	}

	/**
	 * This method is used to shift the data in array A.
	 * @param A
	 ////////////////////////////////////////////////////	//
	 功能：将数组A中的四个字节循环左移一个字节；
	 ////////////////////////////////////////////////////	//
	 */
	public void RotByte(char[] A) {
		char temp;
		temp = A[0];
		A[0] = A[1];
		A[1] = A[2];
		A[2] = A[3];
		A[3] = temp;
	}

	/**
	 * 密钥扩展的时候进行S盒替换
	 * @param A 是存放四个字节的数组
	 */
	public void SubByte(char[] A) {
		for (int i = 0; i < 4; i++)
			A[i] = S_BOX[A[i]];
	}

	/**
	 * 这个方法是用来得到加密轮数
	 * @param Nb 以32bit为单位的待加密明文的长度
	 * @param Nk 是初始密钥的长度
	 * @return 返回加密轮数（Nr）
	 */
	public int GetRounds(int Nb, int Nk) {
		switch (Nb) {
		case 4:
			switch (Nk) {
			case 4:
				return 10;
			case 6:
				return 12;
			case 8:
				return 14;
			default:
				return 0;
			}
		case 6:
			switch (Nk) {
			case 4:
			case 6:
				return 12;
			case 8:
				return 14;
			default:
				return 0;
			}
		case 8:
			switch (Nk) {
			case 4:
			case 6:
			case 8:
				return 14;
			default:
				return 0;
			}
		default:
			return 0;
		}
	}

	/**
	 * 这个方法是用来进行密钥扩展的
	 * @param strKey  用户输入的密钥
	 * @param Nb 以32bit为单位的待加密明文的长度
	 * @param Nk 以32bit为单位的初始密钥的长度.
	 * @param Nr 加密的轮加密的轮.
	 * @return 扩展后的子密钥存放在数组w中
	 */
	public void KeyExpansion(String strKey, int Nb, int Nk, int Nr) {
		int i = 0;
		for (; i < 4; i++)
			for (int j = 0; j < Nk; j++)
				key[i * Nk + j] = strKey.charAt(i * 4 + j);
		i = 0;
		while (i < Nk) {
			w[i * 4] = key[i * 4];
			w[i * 4 + 1] = key[i * 4 + 1];
			w[i * 4 + 2] = key[i * 4 + 2];
			w[i * 4 + 3] = key[i * 4 + 3];
			i++;
		}
		i = Nk;
		while (i < Nb * (Nr + 1)) {
			char[] temp = new char[4];
			temp[0] = w[(i - 1) * 4 + 0];
			temp[1] = w[(i - 1) * 4 + 1];
			temp[2] = w[(i - 1) * 4 + 2];
			temp[3] = w[(i - 1) * 4 + 3];
			if ((i % Nk) == 0) {
				RotByte(temp);
				SubByte(temp);
				for (int j = 0; j < 4; j++)
					temp[j] ^= Rcon[((i - 1) / Nk) * 4 + j];//与Rcon异或；
			} else if (Nk == 8 && i % Nk == 4)
				SubByte(temp);
			w[i * 4 + 0] = (char) (w[(i - Nk) * 4 + 0] ^ temp[0]);
			w[i * 4 + 1] = (char) (w[(i - Nk) * 4 + 1] ^ temp[1]);
			w[i * 4 + 2] = (char) (w[(i - Nk) * 4 + 2] ^ temp[2]);
			w[i * 4 + 3] = (char) (w[(i - Nk) * 4 + 3] ^ temp[3]);
			i++;
		}
	}

	/**
	 * S盒置换
	 * @param state 为明文块
	 * @param Nb 为以32bit为单位的明文块的大小
	 */
	public void byteSub(char[] state, int Nb) {
		for (int i = 0; i < 4 * Nb; i++)
			state[i] = S_BOX[state[i] % 256];

	}

	/**
	 * 对新的状态矩阵根据不同的分组长度做相应的循环移位运算
	 * @param state 状态数组(新的状态矩阵).
	 * @param Nb 是以32比特为单位的明文块的大小
	 */
	public void ShiftRows(char[] state, int Nb) {
		char[] t = new char[8];
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < Nb; c++)
				t[c] = state[Nb * r + (r + c) % Nb];
			for (int c = 0; c < Nb; c++)
				state[Nb * r + c] = t[c];
		}
	}

	/**
	 * 对新的状态矩阵根据不同的分组长度做相应的列混合运算
	 * @param state 状态数组(新的状态矩阵).
	 * @param Nb 是以32比特为单位的明文块的大小
	 */
	public void MixColumns(char[] state, int Nb) {
		int[] t = new int[4];
		for (int c = 0; c < Nb; c++) {
			for (int r = 0; r < 4; r++)
				t[r] = state[Nb * r + c];
			for (int r = 0; r < 4; r++) {
				state[Nb * r + c] = (char) (Ffmul(0x02, t[r])
						^ Ffmul(0x03, t[(r + 1) % 4]) ^ t[(r + 2) % 4] ^ t[(r + 3) % 4]);
			}
		}
	}

	/**
	 * 返回两个域元素A，B的积;
	 * @param A first number.
	 * @param B second number.
	 * @return the product of A and B.
	 */
	public int Ffmul(int A, int B) {

		//查对数表;
		if (A == 0 || B == 0)
			return 0;
		A = log[A];
		B = log[B];
		A = (A + B) % 0xff;
		//查反对数表;
		A = aLog[A];
		return A;
	}

	/**
	 * 这个方法是用来做轮密钥加法变换的
	 * @param state 状态数组(新的状态矩阵).
	 * @param Nb 是以32比特为单位的明文块的大小
	 * @param round 为当前加密的轮数
	 */
	public void AddRoundKey(char[] state, int Nb, int round) {
		for (int c = 0; c < Nb; c++, round++)
			for (int r = 0; r < 4; r++)
				/* w为子密钥,Nb为明文块的大小,round为当前加密的轮数;*/
				state[r * Nb + c] = (char) (state[r * Nb + c] ^ w[round * 4 + r]);
	}

	/**
	 * 这个方法是在AES加密过程中进行的轮变换，（即加密算法）
	 * @param state 是一个包含要加密的明文数组
	 * @param Nb 以32bit为单位的待加密明文的长度.
	 * @param Nr 用户密钥的长度
	 * @return 密文数组
	 */
	public byte[] Transform(char[] state, int Nb, int Nr) {
		int round = 1;
		/*初始化轮密钥加法*/
		AddRoundKey(state, Nb, 0);
		/*（Nr - 1）轮变换*/
		for (; round < Nr; round++) {
			byteSub(state, Nb);
			ShiftRows(state, Nb);
			MixColumns(state, Nb);
			AddRoundKey(state, Nb, round * Nb);
		}
		/*加密算法的最后一圈变换稍为不同,将MixColumn这一步去掉*/
		byteSub(state, Nb);
		ShiftRows(state, Nb);
		AddRoundKey(state, Nb, round * Nb);
		return CharToByte(state);
	}

	/**
	 *  这个方法是在AES解密过程中进行的逆轮变换，（即解密算法）
	 * @param state 要解密的密文数组
	 * @param Nb 以32bit为单位的待加密明文的长度
	 * @param Nr 用户密钥的长度
	 * @return 解密后的明文数组
	 */
	public byte[] ReTransform(char[] state, int Nb, int Nr) {
		AddRoundKey(state, Nb, Nr * Nb);
		for (int round = Nr - 1; round >= 1; round--) {
			InvShiftRows(state, Nb);
			InvByteSub(state, Nb);
			AddRoundKey(state, Nb, round * Nb);
			InvMixColumns(state, Nb);
		}
		InvShiftRows(state, Nb);
		InvByteSub(state, Nb);
		AddRoundKey(state, Nb, 0);
		return CharToByte(state);
	}

	/**
	 * 解密时的S盒逆置换；
	 * @param state 为密文块；
	 * @param Nb Nb为密文块的大小；
	 */
	public void InvByteSub(char[] state, int Nb) {
		for (int i = 0; i < 4 * Nb; i++)
			state[i] = S_BOX_1[state[i] % 256];
	}

	/**
	 * 解密的时候的右移位变换
	 * @param state 为密文块
	 * @param Nb 为密文块的大小
	 */
	public void InvShiftRows(char[] state, int Nb) {
		char[] t = new char[8];
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < Nb; c++)
				t[(c + r) % Nb] = state[r * Nb + c];
			for (int c = 0; c < Nb; c++)
				state[r * Nb + c] = t[c];
		}
	}

	/**
	 * 解密时的列混合变换
	 * @param state 为密文块
	 * @param  Nb 为密文块的大小
	 */
	public void InvMixColumns(char[] state, int Nb) {
		char[] t = new char[4];
		for (int c = 0; c < Nb; c++) {
			for (int r = 0; r < 4; r++)
				t[r] = state[Nb * r + c];
			for (int r = 0; r < 4; r++) {
				state[Nb * r + c] = (char) (Ffmul(0x0e, t[r])
						^ Ffmul(0x0b, t[(r + 1) % 4])
						^ Ffmul(0x0d, t[(r + 2) % 4]) ^ Ffmul(0x09,
						t[(r + 3) % 4]));
			}
		}
	}

	/**
	 * This method is used to transform a array from byte type to char type.
	 * @param data a byte type array.
	 * @return a char type array.
	 */
	public static char[] ByteToChar(byte[] data) {
		char[] A = new char[data.length];
		for (int i = 0; i < data.length; i++)
			A[i] = (char) data[i];
		return A;
	}

	/**
	 * This method is used to transform a array from char type to byte type.
	 * @param data a char type array.
	 * @return a byte type array.
	 */
	public static byte[] CharToByte(char[] data) {
		byte[] A = new byte[data.length];
		for (int i = 0; i < data.length; i++)
			A[i] = (byte) data[i];
		return A;
	}
}
